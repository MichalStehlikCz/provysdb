package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Expression;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Backing for COALESCE SQL function.
 *
 * @param <T> is type of expression, as determined by the first operand
 */
final class FuncCoalesce<T> extends ColumnExpressionBase<T> {

  private final Expression<T> first;
  private final List<Expression<? extends T>> expressions;

  @SafeVarargs
  FuncCoalesce(Expression<T> first, Expression<? extends T>... expressions) {
    this.first = Objects.requireNonNull(first);
    this.expressions = List.copyOf(Arrays.asList(expressions));
  }

  @Override
  public void appendExpression(CodeBuilder builder) {
    builder.append("COALESCE(").apply(first::appendExpression);
    for (var expression : expressions) {
      builder.append(", ").apply(expression::appendExpression);
    }
    builder.append(')');
  }

  @Override
  public Class<T> getType() {
    return first.getType();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FuncCoalesce<?> that = (FuncCoalesce<?>) o;
    return Objects.equals(first, that.first)
        && Objects.equals(expressions, that.expressions);
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (expressions != null ? expressions.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FuncCoalesce{"
        + "first=" + first
        + ", expressions=" + expressions
        + '}';
  }
}
