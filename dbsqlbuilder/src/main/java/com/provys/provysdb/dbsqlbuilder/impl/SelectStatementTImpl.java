package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.DbRowMapper;
import com.provys.provysdb.dbsqlbuilder.BindVariable;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("WeakerAccess")
abstract class SelectStatementTImpl<S extends SelectStatementTImpl> {

    private static final Logger LOG = LogManager.getLogger(SelectStatementTImpl.class);
    @Nonnull
    private final String sqlText;
    @Nonnull
    private final DbPreparedStatement statement;
    private final Map<String, BindWithPos> binds;
    /** If selectStatement retrieved connection from context, it will be kept here and returned to pool on close;
     * when connection is passed to constructor, it is not held here and thus not closed */
    @Nullable
    private DbConnection connection;
    private boolean closed = false;

    @Nonnull
    private static Map<String, BindWithPos> getBinds(List<BindName> binds) {
        // first construct list of positions for each supplied bind name
        int pos = 0;
        Map<BindName, List<Integer>> bindPosMap = new HashMap<>(binds.size());
        for (var bind : binds) {
            var posList = bindPosMap.computeIfAbsent(bind, val -> new ArrayList<>(3));
            posList.add(pos++);
        }
        // and then convert bind + list of positions to BindWithPos
        Map<String, BindWithPos> result = new HashMap<>(bindPosMap.size());
        for (var bindWithPos : bindPosMap.entrySet()) {
            result.put(bindWithPos.getKey().getName(), new BindWithPos(bindWithPos.getKey(), bindWithPos.getValue()));
        }
        return result;
    }

    SelectStatementTImpl(String sqlText, List<BindName> binds, DbSql sqlContext) {
        this.sqlText = sqlText;
        this.connection = sqlContext.getConnection();
        try {
            this.statement = this.connection.prepareStatement(sqlText);
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + sqlText, e);
        }
        this.binds = getBinds(binds);
    }

    SelectStatementTImpl(Select select, DbSql sqlContext) {
        this(select.getSqlText(), select.getBinds(), sqlContext);
    }

    SelectStatementTImpl(Select select, DbConnection connection) {
        this.sqlText = select.getSqlText();
        this.connection = null;
        try {
            this.statement = connection.prepareStatement(select.getSqlText());
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + select, e);
        }
        this.binds = getBinds(select.getBinds());
    }

    /**
     * @return value of field statement
     */
    @Nonnull
    DbPreparedStatement getStatement() {
        return statement;
    }

    @Nonnull
    abstract S self();

    @Nonnull
    public S bindValue(String bind, @Nullable Object value) {
        if (closed) {
            throw new InternalException(LOG, "Attempt to bind value in closed statement " + this);
        }
        var oldValue = binds.get(bind);
        if (oldValue == null) {
            throw new InternalException(LOG, "Bind variable with name " + bind + " not found in statement " + this);
        }
        oldValue.setValue(value);
        return self();
    }

    @Nonnull
    public <T> S bindValue(BindValueT<T> bind, @Nullable T value) {
        return bindValue(bind.getName(), value);
    }

    @Nonnull
    public Collection<BindName> getBinds() {
        return binds.values().stream().map(BindWithPos::getBind).collect(Collectors.toUnmodifiableList());
    }

    private void bindValues() {
        for (var bind : binds.values()) {
            bind.bindValue(statement);
        }
    }

    public DbResultSet execute() {
        if (closed) {
            throw new InternalException(LOG, "Attempt to execute closed statement " + this);
        }
        bindValues();
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new InternalException(LOG, "Error executing statement " + this, e);
        }
    }

    public void close() {
        if (closed) {
            // repeated attempt to close statement should do nothing
            return;
        }
        closed = true;
        SQLException exception = null;
        try {
            statement.close();
        } catch (SQLException e) {
            // even if closing prepared statement failed, we will still try to close connection
            exception = e;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                if (exception == null) {
                    throw new InternalException(LOG, "Error closing connection", e);
                }
            }
            connection = null;
        }
        if (exception != null) {
            throw new InternalException(LOG, "Error closing prepared statement", exception);
        }
    }

    private static class BindWithPos {
        private BindName bind;
        private final List<Integer> positions;
        /** indicates if bind value has been modified after last time it has been bound to prepared statement */
        private boolean modified = true;

        BindWithPos(BindName bind, List<Integer> positions) {
            if (bind instanceof BindVariable) {
                this.bind = bind;
            } else if (bind instanceof BindValueT) {
                this.bind = new BindVariableImpl<>((BindValueT<?>) bind);
            } else {
                // we do not have the value... we store bind-name and wait for value
                this.bind = bind;
            }
            this.positions = List.copyOf(positions);
        }

        /**
         * @return value of field bind
         */
        BindName getBind() {
            return bind;
        }

        /**
         * Set value to given bind
         *
         * @param value is new value to be set
         */
        public void setValue(@Nullable Object value) {
            var combinedBind = bind.withValue(value);
            if (combinedBind == bind) {
                return;
            }
            bind = combinedBind;
            modified = true;
        }

        /**
         * Set value to given bind
         *
         * @param bind is new value to be set
         */
        public void setBind(BindName bind) {
            var combinedBind = this.bind.combine(bind);
            if (combinedBind == this.bind) {
                return;
            }
            this.bind = combinedBind;
            modified = true;
        }

        void bindValue(DbPreparedStatement statement) {
            if (!modified) {
                // if value has not been modified, we do not have to rebind it
                return;
            }
            if (!(bind instanceof BindVariable)) {
                throw new InternalException(LOG, "Value not assigned to bind variable " + bind.getName());
            }
            BindVariable bindVariable = (BindVariable) bind;
            for (var position : positions) {
                bindVariable.bind(statement, position);
            }
            modified = false;
        }
    }

    @Nonnull
    public <T> T fetchOneNoClose(DbRowMapper<T> rowMapper) {
        T result;
        try (var resultSet = execute()) {
            if (!resultSet.next()) {
                throw new InternalException(LOG, "Exact fetch returned no rows" + this);
            }
            result = rowMapper.map(resultSet, 0);
            if (resultSet.next()) {
                throw new InternalException(LOG, "Exact fetch returned more than one row" + this);
            }
        } catch (SQLException e) {
            throw new InternalException(LOG, "Exception thrown by sql statement " + this);
        }
        return result;
    }

    @Nonnull
    public <T> List<T> fetchNoClose(DbRowMapper<T> rowMapper) {
        List<T> result = new ArrayList<>();
        try (var resultSet = execute()) {
            long row = 0;
            while (resultSet.next()) {
                result.add(rowMapper.map(resultSet, row++));
            }
        } catch (SQLException e) {
            throw new InternalException(LOG, "Exception thrown by sql statement " + this);
        }
        return result;
    }

    @Nonnull
    private <T> Stream<T> stream(DbRowMapper<T> rowMapper, boolean close) {
        var resultSet = execute();
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(new DbResultSetIterator<>(rowMapper, resultSet),
                        Spliterator.ORDERED), false)
                .onClose(() -> {
                    // close both result and this statement
                    Exception exception = null;
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        exception = e;
                    }
                    if (close) {
                        this.close();
                    }
                    if (exception != null) {
                        throw new InternalException(LOG, "Error fetching data from statement " + this, exception);
                    }
                });
    }

    @Nonnull
    public <T> T fetchOne(DbRowMapper<T> rowMapper) {
        try {
            return fetchOneNoClose(rowMapper);
        } finally {
            close();
        }
    }

    @Nonnull
    public <T> List<T> fetch(DbRowMapper<T> rowMapper) {
        try {
            return fetchNoClose(rowMapper);
        } finally {
            close();
        }
    }

    @Nonnull
    public <T> Stream<T> stream(DbRowMapper<T> rowMapper) {
        return stream(rowMapper, true);
    }

    @Nonnull
    public <T> Stream<T> streamNoClose(DbRowMapper<T> rowMapper) {
        return stream(rowMapper, false);
    }

    private static class DbResultSetIterator<T> implements Iterator<T> {

        private static final Logger LOG = LogManager.getLogger(DbResultSetIterator.class);

        @Nonnull
        private final DbRowMapper<T> rowMapper;
        @Nonnull
        private final DbResultSet resultSet;
        private long rowNumber = 0;
        private boolean finished = false;
        @Nullable
        private T next = null;

        DbResultSetIterator(DbRowMapper<T> rowMapper, DbResultSet resultSet) {
            this.rowMapper = rowMapper;
            this.resultSet = resultSet;
        }

        private void fetch() {
            if ((finished) || (next != null)) {
                return;
            }
            try {
                if (!resultSet.next()) {
                    finished = true;
                } else {
                    next = rowMapper.map(resultSet, rowNumber++);
                }
            } catch (SQLException e) {
                throw new InternalException(LOG, "Error fetching data in stream", e);
            }
        }

        @Override
        public boolean hasNext() {
            fetch();
            return (!finished);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var result = next;
            next = null;
            return result;
        }
    }
}
