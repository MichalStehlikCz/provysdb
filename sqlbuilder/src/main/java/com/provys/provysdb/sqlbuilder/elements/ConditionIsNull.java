package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.Expression;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ConditionIsNull implements Condition {

  private final Expression<?> expression;

  ConditionIsNull(Expression<?> expression) {
    this.expression = Objects.requireNonNull(expression);
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public void appendExpression(CodeBuilder builder) {
    builder.append('(');
    expression.appendExpression(builder);
    builder.append(" IS NULL)");
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionIsNull that = (ConditionIsNull) o;
    return Objects.equals(expression, that.expression);
  }

  @Override
  public int hashCode() {
    return expression != null ? expression.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ConditionIsNull{"
        + "expression=" + expression
        + '}';
  }
}
