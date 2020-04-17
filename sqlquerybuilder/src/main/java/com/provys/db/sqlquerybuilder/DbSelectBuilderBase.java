package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.SelectT;
import com.provys.db.sqlquery.query.SelectStatement;

/**
 * Common ancestor for all select builder classes that support production of actual select
 * statement.
 *
 * @param <T> is actual type of select builder
 */
interface DbSelectBuilderBase<S extends SelectStatement, Q extends SelectT<Q>,
    T extends DbSelectBuilderBase<S, Q, T>> extends DbSelectBuilderT<T> {

  /**
   * Build select query from content of this builder.
   *
   * @return select query built from content of this builder
   */
  Q buildSelect();

  /**
   * Build select statement from content of this builder.
   *
   * @return select statement built from content of this builder
   */
  S build();
}
