package com.provys.db.sql;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents single query in FROM clause.
 */
public interface FromElement extends Element {

  /**
   * Alias associated with this from element. Might actually be either alias or table name,
   * potentially including scheme
   *
   * @return alias associated with this from element
   */
  @Nullable NamePath getAlias();

  /**
   * Indicate if this element matches specified path. Runs match on its alias.
   *
   * @param path is path to be matched
   * @return is this from elements alias (or table name if no alias is specified) matches supplied
   *     path
   */
  default boolean match(NamePath path) {
    var alias = getAlias();
    if (alias == null) {
      return false;
    }
    return alias.match(path);
  }

  /**
   * Transfer from element to specified context.
   *
   * @param targetContext is target context
   * @param <J>           is subtype of {@code FromElement} cloning will result in based on target
   *                      context
   * @return from element cloned to specified context
   */
  default <J extends FromElement> J transfer(Context<?, ?, ?, ?, J, ?, ?> targetContext) {
    return transfer(targetContext, null);
  }

  /**
   * Transfer from element to specified context.
   *
   * @param targetContext is target context
   * @param bindMap       is new mapping of bind variables; when not specified, bind variables are
   *                      left as they are
   * @param <J>           is subtype of {@code FromElement} cloning will result in based on target
   *                      context
   * @return from element cloned to specified context
   */
  <J extends FromElement> J transfer(Context<?, ?, ?, ?, J, ?, ?> targetContext,
      @Nullable BindMap bindMap);
}
