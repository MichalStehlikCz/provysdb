package com.provys.provysdb.sqlbuilder.elements;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Adds support for keeping information about column type.
 *
 * @param <T> is type of value, represented by column
 */
abstract class SqlColumnType<T> extends SqlColumnBase<T> {

  private final Class<T> type;

  SqlColumnType(Class<T> type) {
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
    SqlColumnType<?> that = (SqlColumnType<?>) o;
    return Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlColumnType{"
        + "type=" + type
        + ", " + super.toString() + '}';
  }
}
