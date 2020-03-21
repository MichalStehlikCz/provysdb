package com.provys.db.sql;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Expression - literal, column, bind, function call, condition or any combination of these,
 * connected by operands.
 */
@SuppressWarnings("CyclicClassDependency")// cyclic dependency on factory Context to support cloning
public interface Expression extends Element {

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  Class<?> getType();

  /**
   * Transfer expression to specified context.
   *
   * @param targetContext is target context
   * @param fromContext is from context to which expression is being migrated
   * @param bindMap is new mapping of bind variables; when not specified, bind variables are left as
   *               they are
   * @param <E> is subtype of {@code Expression} cloning will result in based on target context
   * @return expression cloned to specified context
   */
  <E extends Expression> E transfer(Context<?, ?, ?, ?, ?, ?, E> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap);
}
