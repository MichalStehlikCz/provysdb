package com.provys.db.sqlquery.literals;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface SqlLiteralHandler {

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @param <T>   represents Java type of literal for compile-time type safety
   * @return string representing literal for given value
   */
  <T> String getLiteral(@Nullable T value, Class<T> type);

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @return string representing literal for given value
   */
  String getLiteral(Object value);

  /**
   * Append literal corresponding to given value to supplied builder.
   *
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @param <T>   represents Java type of literal for compile-time type safety
   */
  <T> void appendLiteral(StringBuilder builder, @Nullable T value, Class<T> type);

  /**
   * Append literal corresponding to given value.
   *
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value is value literal should be created for
   */
  void appendLiteral(StringBuilder builder, Object value);
}
