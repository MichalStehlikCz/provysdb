package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.functions.ConditionalOperator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class CombiningConditionBuilder implements ConditionBuilder {

  private final ConditionalOperator operator;
  private final List<Condition> conditions;

  CombiningConditionBuilder(ConditionalOperator operator) {
    this.operator = operator;
    this.conditions = new ArrayList<>(3);
  }

  void add(Condition condition) {
    conditions.add(condition);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CombiningConditionBuilder that = (CombiningConditionBuilder) o;
    return operator == that.operator
        && Objects.equals(conditions, that.conditions);
  }

  @Override
  public int hashCode() {
    int result = operator.hashCode();
    result = 31 * result + conditions.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CombiningConditionBuilder{"
        + "operator=" + operator
        + ", conditions=" + conditions
        + '}';
  }
}
