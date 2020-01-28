package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.ExpressionT;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Backing functions for COALESCE SQL function
 *
 * @param <T> is type of expression, as determined by the first operand
 */
class FuncCoalesce<T> implements ExpressionT<T> {

    private final ExpressionT<T> first;
    private final List<ExpressionT<? extends T>> expressions;

    @SafeVarargs
    FuncCoalesce(ExpressionT<T> first, ExpressionT<? extends T>... expressions) {
        this.first = Objects.requireNonNull(first);
        this.expressions = List.copyOf(Arrays.asList(expressions));
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("COALESCE(").apply(first::addSql);
        for (var expression : expressions) {
            builder.append(", ").apply(expression::addSql);
        }
        builder.append(')');
    }

    @Nonnull
    @Override
    public Class<T> getType() {
        return first.getType();
    }
}
