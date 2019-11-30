package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.LiteralT;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class LiteralOptional<T> implements LiteralT<Optional<T>> {

    @Nonnull
    private final LiteralT<T> literal;

    public LiteralOptional(LiteralT<T> literal) {
        this.literal = Objects.requireNonNull(literal);
    }

    @Nonnull
    @Override
    public Optional<T> getValue() {
        return Optional.of(literal.getValue());
    }

    @Nonnull
    @Override
    public LiteralT<Optional<Optional<T>>> asNullable() {
        return new LiteralOptional<>(this);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        literal.addSql(builder);
    }
}
