package com.provys.db.sqlquery.query;

import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Value class with two fields with values of types T1 and T2 (pair).
 *
 * @param <T1> is type of first field
 * @param <T2> is type of second field
 */
public final class TupleT2<T1, T2> {

  private final @Nullable T1 value1;
  private final @Nullable T2 value2;

  public TupleT2(@Nullable T1 value1, @Nullable T2 value2) {
    this.value1 = value1;
    this.value2 = value2;
  }

  /**
   * The value of the first field.
   *
   * @return value of the first field
   */
  public @Nullable T1 get1() {
    return value1;
  }

  /**
   * The value of the second field.
   *
   * @return value of the second field
   */
  public @Nullable T2 get2() {
    return value2;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TupleT2<?, ?> tupleT2 = (TupleT2<?, ?>) o;
    return Objects.equals(value1, tupleT2.value1)
        && Objects.equals(value2, tupleT2.value2);
  }

  @Override
  public int hashCode() {
    int result = value1 != null ? value1.hashCode() : 0;
    result = 31 * result + (value2 != null ? value2.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "TupleT2{"
        + "value1=" + value1
        + ", value2=" + value2
        + '}';
  }
}
