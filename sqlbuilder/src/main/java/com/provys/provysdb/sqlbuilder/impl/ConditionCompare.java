package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.ExpressionT;
import com.provys.provysdb.sqlparser.SqlSymbol;

import javax.annotation.Nonnull;
import java.util.Objects;

class ConditionCompare implements Condition {

    @Nonnull
    private final Expression first;
    @Nonnull
    private final Expression second;
    @Nonnull
    private final SqlSymbol comparison;

    <T> ConditionCompare(ExpressionT<T> first, ExpressionT<T> second, SqlSymbol comparison) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
        if (!comparison.isComparison()) {
            throw new InternalException("Invalid comparison - symbol " + comparison.getSymbol() + "not valid");
        }
        this.comparison = comparison;
    }

    @Override
    public boolean isEmpty() {
        return (comparison == SqlSymbol.EQUAL) && first.equals(second);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('(');
        first.addSql(builder);
        builder.append(comparison.getSymbol());
        second.addSql(builder);
        builder.append(')');
    }
}
