package com.provys.db.query.elements;

import java.util.Collection;

/**
 * Part of QueryConsumer used to consume select clause.
 */
public interface SelectClauseConsumer {

  /**
   * Consume select part of query, based on supplied columns.
   *
   * @param columns is collection of columns to be used for result projection
   */
  void selectColumns(Collection<? extends SelectColumn<?>> columns);
}
