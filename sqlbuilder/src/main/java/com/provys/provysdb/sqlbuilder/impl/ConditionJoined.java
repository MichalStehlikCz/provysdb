package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import java.util.Collection;
import java.util.List;

final class ConditionJoined implements Condition {

  private final SqlConditionOperator operator;
  private final List<Condition> conditions;

  ConditionJoined(SqlConditionOperator operator, Collection<Condition> conditions) {
    this.operator = operator;
    this.conditions = List.copyOf(conditions);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    if (conditions.size() > 1) {
      builder.append('(').appendLine().increasedIdent("", operator.toString() + " ", 2);
    }
    for (var condition : conditions) {
      condition.addSql(builder);
      builder.appendLine();
    }
    if (conditions.size() > 1) {
      builder.popIdent().append(')');
    }
  }

  SqlConditionOperator getOperator() {
    return operator;
  }

  Collection<Condition> getConditions() {
    return conditions;
  }

  @Override
  public boolean isEmpty() {
    return conditions.isEmpty();
  }

  @Override
  public String toString() {
    return "ConditionJoined{"
        + "operator=" + operator
        + ", conditions=" + conditions
        + '}';
  }
}
