package com.provys.db.querybuilder.elements;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Adds support for keeping explicit information about column expression type.
 *
 * @param <T> is type of value, represented by column
 */
abstract class ColumnExpressionBaseWithType<T> extends ColumnExpressionBase<T> {

  private final Class<T> type;

  ColumnExpressionBaseWithType(Class<T> type) {
    this.type = type;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnExpressionBaseWithType<?> that = (ColumnExpressionBaseWithType<?>) o;
    return Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ColumnExpressionBaseWithType{"
        + "type=" + type
        + ", " + super.toString() + '}';
  }
}
