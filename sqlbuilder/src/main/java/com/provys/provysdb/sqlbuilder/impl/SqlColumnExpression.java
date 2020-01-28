package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

class SqlColumnExpression extends SqlColumnBase {

    @Nonnull
    private final Expression expression;

    SqlColumnExpression(Expression expression, @Nullable SqlIdentifier alias) {
        super(alias);
        this.expression = Objects.requireNonNull(expression);
    }

    @Nonnull
    @Override
    public SqlColumn withAlias(SqlIdentifier alias) {
        if (alias.equals(getAlias().orElse(null))) {
            return this;
        }
        return new SqlColumnExpression(expression, alias);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        expression.addSql(builder);
    }
}
