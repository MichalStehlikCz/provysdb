package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

public interface SqlColumnT<T> extends SqlColumn, ExpressionT<T> {

    /**
     * @return Java type this column should be mapped to. Used to find proper adapter for value retrieval
     */
    Class<T> getType();

    @Nonnull
    SqlColumnT<T> withAlias(SqlIdentifier alias);
}
