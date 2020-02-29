package com.provys.provysdb.sqlbuilder;

/**
 * Sql element represents any element of sql statement. Element's only ability is export its sql
 * representation.
 */
public interface SqlElement {

  /**
   * Appends sql text to code builder, used to construct statement. Also add associated binds
   *
   * @param builder is CodeBuilder, used to construct sql text
   */
  void addSql(CodeBuilder builder);
}
