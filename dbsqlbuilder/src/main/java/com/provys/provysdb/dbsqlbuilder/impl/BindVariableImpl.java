package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbsqlbuilder.BindVariableT;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Optional;

class BindVariableImpl<T> implements BindVariableT<T> {

    private static final Logger LOG = LogManager.getLogger(BindVariableImpl.class);

    @Nonnull
    private final BindValueT<T> bindValue;

    BindVariableImpl(BindValueT<T> bindValue) {
        this.bindValue = bindValue;
    }

    @Override
    @Nonnull
    public Class<T> getType() {
        return bindValue.getType();
    }

    @Override
    @Nonnull
    public Optional<T> getValue() {
        return bindValue.getValue();
    }

    @Override
    @Nonnull
    public BindVariableT<T> withValue(@Nullable Object value) {
        var result = bindValue.withValue(value);
        if (result == this) {
            return this;
        }
        if (result instanceof BindVariableT) {
            return (BindVariableT<T>) result;
        }
        return new BindVariableImpl<>(result);
    }

    @Override
    @Nonnull
    public <U> Optional<U> getValue(Class<U> type) {
        return bindValue.getValue(type);
    }

    @Override
    @Nonnull
    public BindVariableT<T> combine(BindName other) {
        var result = bindValue.combine(other);
        if (result == this) {
            return this;
        }
        if (result instanceof BindVariableT) {
            return (BindVariableT<T>) result;
        }
        return new BindVariableImpl<>(result);
    }

    @Override
    @Nonnull
    public String getName() {
        return bindValue.getName();
    }

    @Override
    public void addSql(CodeBuilder builder) {
        bindValue.addSql(builder);
    }

    @Override
    public void bind(DbPreparedStatement statement, int parameterIndex) {
        var value = getValue().orElse(null);
        try {
            statement.setValue(parameterIndex, getType(), value);
        } catch (SQLException e) {
            throw new InternalException(LOG, "Error binding value " + value + '(' + getType() + ") to statement " +
                    statement, e);
        }
    }
}
