package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

public interface LiteralT<T> extends ExpressionT<T> {
    /**
     * @return value this literal represents
     */
    @Nonnull
    T getValue();
}
