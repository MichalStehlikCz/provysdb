package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;

class ConditionSimple implements Condition {

  private final String sql;

  ConditionSimple(String sql) {
    this.sql = sql;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('(').append(sql).append(')');
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public String toString() {
    return "ConditionSimple{"
        + "sql='" + sql + '\''
        + '}';
  }
}
