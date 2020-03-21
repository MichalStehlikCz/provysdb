package com.provys.db.sql;

import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("CyclicClassDependency")// cyclic dependency on factory Context to support cloning
public interface SelectClause extends Element {

  /**
   * Transfer select clause to specified context.
   *
   * @param targetContext is target context
   * @param bindMap is new mapping of bind variables; when not specified, bind variables are left as
   *               they are
   * @param <E> is subtype of {@code SelectClause} cloning will result in based on target context
   * @return select clause cloned to specified context
   */
  <E extends SelectClause> E transfer(Context<?, E, ?, ?, ?, ?, ?> targetContext,
      @Nullable BindMap bindMap);
}
