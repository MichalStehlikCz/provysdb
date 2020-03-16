package com.provys.db.sql;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents single query in FROM clause.
 */
public interface FromElement extends Element {

  /**
   * Alias associated with this from element.
   *
   * @return alias associated with this from element
   */
  @Nullable SimpleName getAlias();

  /**
   * Transfer from element to specified context.
   *
   * @param targetContext is target context
   * @param <J> is subtype of {@code FromElement} cloning will result in based on target context
   * @return from element cloned to specified context
   */
  default <J extends FromElement> J transfer(Context<?, ?, ?, ?, J, ?, ?> targetContext) {
    return transfer(targetContext, null);
  }

  /**
   * Transfer from element to specified context.
   *
   * @param targetContext is target context
   * @param bindMap is new mapping of bind variables; when not specified, bind variables are left as
   *               they are
   * @param <J> is subtype of {@code FromElement} cloning will result in based on target context
   * @return from element cloned to specified context
   */
  <J extends FromElement> J transfer(Context<?, ?, ?, ?, J, ?, ?> targetContext,
      @Nullable BindMap bindMap);
}
