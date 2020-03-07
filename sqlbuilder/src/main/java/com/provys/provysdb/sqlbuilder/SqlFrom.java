package com.provys.provysdb.sqlbuilder;

/**
 * Interface represents element of from clause - e.g. either just table to be selected from or join
 * clause.
 */
public interface SqlFrom extends SqlElement {

  /**
   * Alias associated with given expression.
   *
   * @return alias this table is associated with; mandatory for element in from clause, as even
   *     though it is not required in SQL, it is enforced by PROVYS StyleGuide
   */
  SqlTableAlias getAlias();
}
