package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

final class SqlFromDual extends SqlFromBase {

  private static final SqlFromDual INSTANCE = new SqlFromDual();

  static SqlFromDual getInstance() {
    return INSTANCE;
  }

  private SqlFromDual() {
    super(new QueryAliasImpl("dual"));
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("dual");
  }

  @Override
  public String toString() {
    return "SqlFromDual{" + super.toString() + '}';
  }
}
