package com.provys.db.sqlquery.literals;

import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Translates value to literal, representing given value in Sql.
 *
 * @param <T> is Java type this handler is usable for
 */
public interface SqlLiteralTypeHandler<T> extends Serializable {

  /**
   * Class this handler can produce literals for.
   *
   * @return class this adapter is associated with
   */
  Class<T> getType();

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @return string representing literal for given value
   */
  String getLiteral(@Nullable T value);

  /**
   * Append literal to StringBuilder. Generally similar to getLiteral, might be more effective in
   * case we built literal by sticking pieces together
   *
   * @param builder is builder we want to append literal to
   * @param value is value literal should be created for
   */
  default void appendLiteral(StringBuilder builder, @Nullable T value) {
    builder.append(getLiteral(value));
  }
}
