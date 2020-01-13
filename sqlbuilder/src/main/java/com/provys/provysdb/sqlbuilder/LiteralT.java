package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Literal of specified type, implemented as special case of expression
 *
 * @param <T> is type of literal's value
 */
public interface LiteralT<T> extends ExpressionT<T> {
    /**
     * @return value this literal represents
     */
    @Nonnull
    T getValue();
}
