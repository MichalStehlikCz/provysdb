package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.ExpressionT;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Backing function for NVL SQL function.
 *
 * @param <T> is type, defined by the first operand
 */
final class FuncNvl<T> implements ExpressionT<T> {

  private final ExpressionT<T> first;
  private final ExpressionT<? extends T> second;

  FuncNvl(ExpressionT<T> first, ExpressionT<? extends T> second) {
    this.first = Objects.requireNonNull(first);
    this.second = Objects.requireNonNull(second);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("NVL(").apply(first::addSql).append(", ").apply(second::addSql).append(')');
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
    FuncNvl<?> funcNvl = (FuncNvl<?>) o;
    return Objects.equals(first, funcNvl.first)
        && Objects.equals(second, funcNvl.second);
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (second != null ? second.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FuncNvl{"
        + "first=" + first
        + ", second=" + second
        + '}';
  }
}
