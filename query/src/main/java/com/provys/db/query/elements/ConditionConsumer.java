package com.provys.db.query.elements;

import com.provys.db.query.functions.ConditionalOperator;
import java.util.Collection;

/**
 * Part of query consumer that can consume conditions.
 */
public interface ConditionConsumer {

  /**
   * Consume condition, based on supplied operator and arguments.
   *
   * @param operator is condition operator / function
   * @param arguments are supplied arguments
   */
  void condition(ConditionalOperator operator, Collection<? extends Expression<?>> arguments);
}
