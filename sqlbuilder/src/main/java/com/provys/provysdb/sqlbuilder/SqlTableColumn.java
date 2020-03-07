package com.provys.provysdb.sqlbuilder;

/**
 * SqlTableColumn adds option to change alias of base table. Used mostly in generated code, where
 * default table alias is used, but might be modified on application
 *
 * @param <T> is Java type this column evaluates to
 */
public interface SqlTableColumn<T> extends SqlColumn<T> {

  @Override
  SqlTableColumn<T> as(SqlIdentifier newAlias);

  /**
   * Replace column's current table alias with specified one.
   *
   * @param newTableAlias is new alias for table column is based on
   * @return new table column with table alias replaced with specified one
   */
  SqlTableColumn<T> withTableAlias(SqlTableAlias newTableAlias);
}
