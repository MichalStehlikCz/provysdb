package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface LiteralT<T> extends ExpressionT<T> {
    /**
     * @return value this literal represents
     */
    @Nonnull
    T getValue();

    @Override
    @Nonnull
    LiteralT<Optional<T>> asNullable();
}
