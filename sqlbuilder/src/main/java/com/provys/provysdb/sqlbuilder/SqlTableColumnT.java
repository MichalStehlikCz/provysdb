package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Typed version of table column interface
 *
 * @param <T> is Java type this column evaluates to
 */
public interface SqlTableColumnT<T> extends SqlColumnT<T>, SqlTableColumn {

    @Nonnull
    SqlTableColumnT<T> withAlias(SqlIdentifier alias);

    @Nonnull
    SqlTableColumnT<T> withTableAlias(SqlTableAlias tableAlias);
}
