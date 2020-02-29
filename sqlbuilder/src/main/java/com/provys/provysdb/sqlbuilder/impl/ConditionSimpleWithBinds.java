package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ConditionSimpleWithBinds implements Condition {

  private final String sql;
  private final List<BindName> binds;

  ConditionSimpleWithBinds(String sql, Collection<BindName> binds) {
    this.sql = sql;
    this.binds = new ArrayList<>(binds);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('(').append(sql).append(')');
    builder.addBinds(binds);
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public String toString() {
    return "ConditionSimpleWithBinds{"
        + "sql='" + sql + '\''
        + ", binds=" + binds
        + '}';
  }
}
