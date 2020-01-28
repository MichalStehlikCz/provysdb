package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

class SqlColumnExpressionT<T> extends SqlColumnBase implements SqlColumnT<T> {

    @Nonnull
    private final ExpressionT<T> expression;

    SqlColumnExpressionT(ExpressionT<T> expression, @Nullable SqlIdentifier alias) {
        super(alias);
        this.expression = Objects.requireNonNull(expression);
    }

    @Nonnull
    @Override
    public SqlColumnT<T> withAlias(SqlIdentifier alias) {
        if (alias.equals(getAlias().orElse(null))) {
            return this;
        }
        return new SqlColumnExpressionT<>(expression, alias);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        expression.addSql(builder);
    }

    @Nonnull
    @Override
    public Class<T> getType() {
        return expression.getType();
    }
}
