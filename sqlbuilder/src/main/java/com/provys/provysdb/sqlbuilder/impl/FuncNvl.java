package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.ExpressionT;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Backing function for NVL SQL function
 *
 * @param <T> is type, defined by the first operand
 */
class FuncNvl<T> implements ExpressionT<T> {

    @Nonnull
    private final ExpressionT<T> first;
    @Nonnull
    private final ExpressionT<? extends T> second;

    FuncNvl(ExpressionT<T> first, ExpressionT<? extends T> second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("NVL(").apply(first::addSql).append(", ").apply(second::addSql).append(')');
    }

    @Nonnull
    @Override
    public Class<T> getType() {
        return first.getType();
    }
}
