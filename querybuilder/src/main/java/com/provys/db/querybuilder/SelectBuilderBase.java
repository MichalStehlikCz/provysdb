package com.provys.db.querybuilder;

import com.provys.db.query.elements.SelectT;

/**
 * Common ancestor for all select builder classes that support production of actual select
 * statement.
 *
 * @param <T> is actual type of select builder
 */
interface SelectBuilderBase<S extends SelectT<S>, T extends SelectBuilderBase<S, T>>
    extends SelectBuilderT<T> {

  /**
   * Build select statement from content of this builder.
   *
   * @return select statement built from content of this builder
   */
  S build();
}
