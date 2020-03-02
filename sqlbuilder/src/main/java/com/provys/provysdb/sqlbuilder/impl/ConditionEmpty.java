package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;

final class ConditionEmpty implements Condition {

  private static final ConditionEmpty INSTANCE = new ConditionEmpty();

  static ConditionEmpty getInstance() {
    return INSTANCE;
  }

  private ConditionEmpty() {
  }

  @Override
  public void addSql(CodeBuilder builder) {
    // we are expected to insert condition...
    builder.append("(1=1)");
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public String toString() {
    return "ConditionEmpty{}";
  }
}