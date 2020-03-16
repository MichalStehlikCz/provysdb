package com.provys.db.sql;

import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("CyclicClassDependency")
// cyclic dependency on factory Context to support cloning
public interface Condition extends Element {

  /**
   * Transfer condition to specified context.
   *
   * @param targetContext is target context
   * @param <O>           is subtype of {@code Condition} cloning will result in based on target
   *                      context
   * @return condition cloned to specified context
   */
  default <O extends Condition> O transfer(Context<?, ?, ?, ?, ?, O, ?> targetContext) {
    return transfer(targetContext, null);
  }

  /**
   * Transfer condition to specified context, replacing bind variables in progress.
   *
   * @param targetContext is target context
   * @param bindMap       is new mapping of bind variables; when not specified, bind variables are
   *                      left as they are
   * @param <O>           is subtype of {@code Condition} cloning will result in based on target
   *                      context
   * @return condition cloned to specified context
   */
  <O extends Condition> O transfer(Context<?, ?, ?, ?, ?, O, ?> targetContext,
      @Nullable BindMap bindMap);
}
