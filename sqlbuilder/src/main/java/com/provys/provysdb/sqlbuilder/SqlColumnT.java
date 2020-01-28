package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Typed column - extends column interface with java type associated with column's content. Allows to prepare typed
 * statements.
 *
 * @param <T> is type of column (of its value)
 */
public interface SqlColumnT<T> extends SqlColumn, ExpressionT<T> {

    @Nonnull
    SqlColumnT<T> withAlias(SqlIdentifier alias);
}
