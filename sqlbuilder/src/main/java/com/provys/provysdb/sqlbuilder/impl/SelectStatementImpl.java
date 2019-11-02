package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SelectStatementImpl implements SelectStatement {

    private static final Logger LOG = LogManager.getLogger(SelectStatementImpl.class);

    @Nonnull
    private final String sqlText;
    /** If selectStatement retrieved connection from context, it will be kept here and returned to pool on close;
     * when connection is passed to constructor, it is not held here and thus not closed */
    @Nullable
    private DbConnection connection;
    @Nonnull
    private final DbPreparedStatement statement;
    private final Map<String, BindWithPos> binds;
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

    SelectStatementImpl(String sqlText, List<BindName> binds, Sql sqlContext) {
        this.sqlText = sqlText;
        this.connection = sqlContext.getConnection();
        try {
            this.statement = this.connection.prepareStatement(sqlText);
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + sqlText, e);
        }
        this.binds = getBinds(binds);
    }

    SelectStatementImpl(Select select, Sql sqlContext) {
        this(select.getSqlText(), select.getBinds(), sqlContext);
    }

    SelectStatementImpl(Select select, DbConnection connection) {
        this.sqlText = select.getSqlText();
        this.connection = null;
        try {
            this.statement = connection.prepareStatement(select.getSqlText());
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + select, e);
        }
        this.binds = getBinds(select.getBinds());
    }

    @Nonnull
    @Override
    public SelectStatement bindValue(String bind, @Nullable Object value) {
        if (closed) {
            throw new InternalException(LOG, "Attempt to bind value in closed statement " + this);
        }
        var oldValue = binds.get(bind);
        if (oldValue == null) {
            throw new InternalException(LOG, "Bind variable with name " + bind + " not found in statement " + this);
        }
        oldValue.setValue(value);
        return this;
    }

    @Nonnull
    @Override
    public <T> SelectStatement bindValue(BindVariableT<T> bind, @Nullable T value) {
        return bindValue(bind.getName(), value);
    }

    @Nonnull
    @Override
    public Collection<BindName> getBinds() {
        return binds.values().stream().map(BindWithPos::getBind).collect(Collectors.toUnmodifiableList());
    }

    private void bindValues() {
        for (var bind : binds.values()) {
            bind.bindValue(statement);
        }
    }

    @Override
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

    @Override
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
            this.bind = bind;
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
            BindVariable bindValue = (BindVariable) bind;
            for (var position : positions) {
                bindValue.bind(statement, position);
            }
            modified = false;
        }
    }
}
