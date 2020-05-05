package com.provys.db.query.elements;

import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Part of query consumer interface, supporting consumption of select queries.
 */
public interface SelectConsumer {

  /**
   * Consume select query based on supplied select, from and where clauses.
   *
   * @param selectClause is select (projection) part of query
   * @param fromClause   is from (sources) part of query
   * @param whereClause  is where (conditions) part of query
   */
  void select(SelectClause selectClause, FromClause fromClause, @Nullable Condition whereClause);

  /**
   * Consume select query based on supplied columns, from and where clauses.
   *
   * @param columns     is collection of columns
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   */
  void select(Collection<? extends SelectColumn<?>> columns, FromClause fromClause,
      @Nullable Condition whereClause);

  /**
   * Consume select query based on supplied column, from and where clauses.
   *
   * @param column1     is the first and only column of query
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   */
  default void select(SelectColumn<?> column1, FromClause fromClause,
      @Nullable Condition whereClause) {
    select(List.of(column1), fromClause, whereClause);
  }

  /**
   * Consume select query based on supplied columns, from and where clauses.
   *
   * @param column1     is the first column of query
   * @param column2     is the second column of query
   * @param fromClause  is from (sources) part of query
   * @param whereClause is where (conditions) part of query
   */
  default void select(SelectColumn<?> column1, SelectColumn<?> column2, FromClause fromClause,
      @Nullable Condition whereClause) {
    select(List.of(column1, column2), fromClause, whereClause);
  }
}
