package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.SimpleName;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents column in select statement. Might be either scalar or more complex.
 *
 * @param <T> is Java type corresponding to values in this column
 */
@Immutable
public interface SelectColumn<T> extends Element<SelectColumn<T>> {

  /**
   * Type of values in this column.
   *
   * @return type of values in this column
   */
  Class<T> getType();

  /**
   * Retrieve alias of this column.
   *
   * @return alias of this column, null when column has no alias
   */
  @Nullable SimpleName getAlias();

  /**
   * Specialized action that passes information about this column to consumer.
   *
   * @param consumer is specialized select column consumer
   */
  void apply(SelectColumnConsumer consumer);

  @Override
  default void apply(QueryConsumer consumer) {
    apply((SelectColumnConsumer) consumer);
  }
}
