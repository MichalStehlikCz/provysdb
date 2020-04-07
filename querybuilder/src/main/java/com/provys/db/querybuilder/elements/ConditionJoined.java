package com.provys.db.querybuilder.elements;

import com.provys.db.querybuilder.Condition;
import com.provys.provysdb.sql.CodeBuilder;
import java.util.Collection;
import java.util.List;

final class ConditionJoined implements Condition {

  private final ConditionOperator operator;
  private final List<Condition> conditions;

  ConditionJoined(ConditionOperator operator, Collection<Condition> conditions) {
    this.operator = operator;
    this.conditions = List.copyOf(conditions);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    if (conditions.size() > 1) {
      builder.append('(').appendLine().increasedIdent("", operator.toString() + ' ', 2);
    }
    for (var condition : conditions) {
      condition.addSql(builder);
      builder.appendLine();
    }
    if (conditions.size() > 1) {
      builder.popIdent().append(')');
    }
  }

  ConditionOperator getOperator() {
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
