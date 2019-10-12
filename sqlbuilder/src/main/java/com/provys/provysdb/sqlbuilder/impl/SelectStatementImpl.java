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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SelectStatementImpl implements SelectStatement {

    private static final Logger LOG = LogManager.getLogger(SelectStatementImpl.class);

    private final Select select;
    private final DbConnection connection;
    private final boolean closeConnection;
    private final DbPreparedStatement statement;
    private final Map<BindName, BindVariable> bindValues;
    private boolean closed = false;

    SelectStatementImpl(Select select, Sql sqlContext) {
        this.select = select;
        this.connection = sqlContext.getConnection();
        this.closeConnection = true;
        try {
            this.statement = this.connection.prepareStatement(select.getSqlText());
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + select, e);
        }
        this.bindValues = select.getBinds().stream()
                .collect(Collectors.toConcurrentMap(BindVariable::getName, Function.identity()));
    }

    SelectStatementImpl(Select select, DbConnection connection) {
        this.select = select;
        this.connection = connection;
        this.closeConnection = false;
        try {
            this.statement = this.connection.prepareStatement(select.getSqlText());
        } catch (SQLException e) {
            throw new InternalException(LOG, "Failed to parse statement " + select, e);
        }
        this.bindValues = select.getBinds().stream()
                .collect(Collectors.toConcurrentMap(BindVariable::getName, Function.identity()));
    }

    @Nonnull
    @Override
    public SelectStatement bindValue(BindName bind, @Nullable Object value) {
        if (closed) {
            throw new InternalException(LOG, "Attempt to bind value in closed statement " + this);
        }
        var oldValue = bindValues.get(bind);
        if (oldValue == null) {
            throw new InternalException(LOG, "Bind with name " + bind + " not found in statement " + this);
        }
        bindValues.put(oldValue.getName(), oldValue.withValue(value));
        return this;
    }

    @Nonnull
    @Override
    public SelectStatement bindValue(String bind, @Nullable Object value) {
        return bindValue(new BindNameImpl(bind), value);
    }

    @Nonnull
    @Override
    public <T> SelectStatement bindValue(BindVariableT<T> bind, @Nullable T value) {
        return bindValue(bind.getName(), value);
    }

    private void bindValue(int position, BindVariable bindValue) {

    }

    private void bindValues() {
        var bindPositions = select.getBindPositions();
        for (var bindPosition : bindPositions.keySet()) {
            var bindValue = bindValues.get(bindPosition.getName());
            for (var position : bindPositions.get(bindPosition)) {
                bindValue(position, bindValue);
            }
        }
    }

    @Override
    public DbResultSet execute() {
        if (closed) {
            throw new InternalException(LOG, "Attempt to execute closed statement " + this);
        }
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
        if (closeConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                if (exception == null) {
                    throw new InternalException(LOG, "Error closing connection", e);
                }
            }
        }
        if (exception != null) {
            throw new InternalException(LOG, "Error closing prepared statement", exception);
        }
    }
}
