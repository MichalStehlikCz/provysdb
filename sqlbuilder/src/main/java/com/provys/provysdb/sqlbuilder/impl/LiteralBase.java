package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.LiteralT;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Common ancestor for literal classes
 */
abstract class LiteralBase<T> implements LiteralT<T> {

    @Nonnull
    private final T value;

    LiteralBase(T value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralBase<?> that = (LiteralBase<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Nonnull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "value=" + value +
                '}';
    }
}
