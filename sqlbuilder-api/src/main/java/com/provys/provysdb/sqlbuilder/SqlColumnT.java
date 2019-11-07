package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

public interface SqlColumnT<T> extends SqlColumn, ExpressionT<T> {

    /**
     * @return class associated with given column
     */
    @Nonnull
    Class<T> getType();
}
