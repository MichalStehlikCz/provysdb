package com.provys.db.sql;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface SelectColumn extends Element {

  /**
   * Transfer select column to specified context.
   *
   * @param targetContext is target context
   * @param bindMap is new mapping of bind variables; when not specified, bind variables are left as
   *               they are
   * @param <C> is subtype of {@code SelectColumn} cloning will result in based on target context
   * @return select column cloned to specified context
   */
  <C extends SelectColumn> C transfer(Context<?, ?, C, ?, ?, ?, ?> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap);
}
