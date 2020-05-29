package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;

/**
 * Expression - literal, column, bind, function call, condition or any combination of these,
 * connected by operands.
 */
@Immutable
public interface Expression<T1> extends Element<Expression<T1>> {

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  Class<T1> getType();

  /**
   * Apply expression on consumer. Allows application on expression consumer, while generic element
   * only supports QueryConsumer.
   *
   * @param consumer is consumer this expression should be applied to
   */
  void apply(ExpressionConsumer consumer);

  @Override
  default void apply(QueryConsumer consumer) {
    apply((ExpressionConsumer) consumer);
  }
}
