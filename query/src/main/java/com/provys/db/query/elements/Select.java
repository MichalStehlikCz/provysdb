package com.provys.db.query.elements;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Immutable object representing sql statement, described in structured way. This statement can be
 * used to retrieve {@code SelectStatement} object for execution. If needed, it can also be cloned
 * to different dialect.
 */
public interface Select extends Element<Select> {

  /**
   * Retrieve select clause of this statement.
   *
   * @return select clause of this statement
   */
  SelectClause getSelectClause();

  /**
   * Retrieve from clause of this statement.
   *
   * @return from clause of this statement
   */
  FromClause getFromClause();

  /**
   * Retrieve where clause of this statement.
   *
   * @return where clause of this statement
   */
  @Nullable Condition getWhereClause();
}
