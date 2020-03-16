package com.provys.provysdb.sqldefault.expression;

import com.provys.provysdb.sql.Literal;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Common ancestor for literal classes.
 */
abstract class LiteralBase<T> implements Literal<T> {

  private final T value;

  LiteralBase(T value) {
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public T getValue() {
    return value;
  }

  @SuppressWarnings("EqualsGetClass")
  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LiteralBase<?> that = (LiteralBase<?>) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "LiteralBase{"
        + "value=" + value
        + '}';
  }
}
