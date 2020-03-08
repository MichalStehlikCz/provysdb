package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.ConditionJoiner;
import com.provys.provysdb.sqlbuilder.impl.SqlConditionOperator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class ConditionJoinerImpl implements ConditionJoiner {

  private final ConditionOperator operator;
  private final List<Condition> conditions;

  ConditionJoinerImpl(ConditionOperator operator) {
    this.operator = Objects.requireNonNull(operator);
    this.conditions = new ArrayList<>(4);
  }

  ConditionJoinerImpl(ConditionOperator operator, Collection<Condition> conditions) {
    this.operator = Objects.requireNonNull(operator);
    if (operator == ConditionOperator.AND) {
      // in AND, we skip trivial condition
      this.conditions = conditions.stream().filter(condition -> !condition.isEmpty())
          .collect(Collectors.toList());
    } else {
      // in OR, trivial condition is handled in build
      this.conditions = new ArrayList<>(conditions);
    }
  }

  @Override
  public ConditionJoiner add(Condition condition) {
    // in case of OR, trivial condition is handled in build; in AND, we can just skip it
    if (!condition.isEmpty() || (operator == ConditionOperator.OR)) {
      conditions.add(condition);
    }
    return this;
  }

  @Override
  public Condition build() {
    if (conditions.isEmpty()) {
      return ConditionEmpty.getInstance();
    }
    if (conditions.size() == 1) {
      return conditions.get(0);
    }
    // in case of OR, we handle trivial condition here...
    if ((operator == ConditionOperator.OR) && conditions.stream().anyMatch(Condition::isEmpty)) {
      return ConditionEmpty.getInstance();
    }
    return new ConditionJoined(operator, conditions);
  }

  @Override
  public String toString() {
    return "ConditionJoinerImpl{"
        + "operator=" + operator
        + ", conditions=" + conditions
        + '}';
  }
}
