package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.ExpressionT;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Backing functions for COALESCE SQL function.
 *
 * @param <T> is type of expression, as determined by the first operand
 */
final class FuncCoalesce<T> implements ExpressionT<T> {

  private final ExpressionT<T> first;
  private final List<ExpressionT<? extends T>> expressions;

  @SafeVarargs
  FuncCoalesce(ExpressionT<T> first, ExpressionT<? extends T>... expressions) {
    this.first = Objects.requireNonNull(first);
    this.expressions = List.copyOf(Arrays.asList(expressions));
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("COALESCE(").apply(first::addSql);
    for (var expression : expressions) {
      builder.append(", ").apply(expression::addSql);
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
