package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnExpression<T> extends SqlColumnBase<T> implements SqlColumn<T> {

  private final Expression<T> expression;

  SqlColumnExpression(Expression<T> expression) {
    this.expression = Objects.requireNonNull(expression);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    expression.addSql(builder);
  }

  @Override
  public Class<T> getType() {
    return expression.getType();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    SqlColumnExpression<?> that = (SqlColumnExpression<?>) o;
    return Objects.equals(expression, that.expression);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (expression != null ? expression.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnExpressionT{"
        + "expression=" + expression
        + ", " + super.toString() + '}';
  }
}
