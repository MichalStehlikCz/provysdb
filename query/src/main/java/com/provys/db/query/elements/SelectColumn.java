package com.provys.db.query.elements;

/**
 * Represents column in select statement. Might be either scalar or more complex.
 *
 * @param <T> is Java type corresponding to values in this column
 */
public interface SelectColumn<T> extends Element<SelectColumn<T>> {

  /**
   * Type of values in this column.
   *
   * @return type of values in this column
   */
  Class<T> getType();
}
