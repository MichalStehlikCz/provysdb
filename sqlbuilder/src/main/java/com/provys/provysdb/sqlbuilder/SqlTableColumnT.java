package com.provys.provysdb.sqlbuilder;

/**
 * Typed version of {@link SqlTableColumn} interface.
 *
 * @param <T> is Java type this column evaluates to
 */
public interface SqlTableColumnT<T> extends SqlColumnT<T>, SqlTableColumn {

    @Override
    SqlTableColumnT<T> withAlias(SqlIdentifier alias);

    @Override
    SqlTableColumnT<T> withTableAlias(SqlTableAlias tableAlias);
}
