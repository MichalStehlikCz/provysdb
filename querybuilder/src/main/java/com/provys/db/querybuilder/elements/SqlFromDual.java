package com.provys.db.querybuilder.elements;

import com.provys.provysdb.sql.CodeBuilder;

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
