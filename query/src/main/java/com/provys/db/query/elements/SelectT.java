package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Base ancestor for Select queries.
 *
 * @param <T> is particular type of select query (distinguished by number and types of columns)
 */
@Immutable
public interface SelectT<T extends SelectT<T>> extends Element<T> {

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
