package com.provys.provysdb.sqlbuilder;

/**
 * Typed column - extends column interface with java type associated with column's content. Allows
 * to prepare typed statements.
 *
 * @param <T> is type of column (of its value)
 */
public interface SqlColumnT<T> extends SqlColumn, ExpressionT<T> {

  @Override
  SqlColumnT<T> withAlias(SqlIdentifier alias);
}
