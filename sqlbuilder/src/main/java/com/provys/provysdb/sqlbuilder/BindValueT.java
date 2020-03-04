package com.provys.provysdb.sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Typed version of bind variable interface.
 *
 * @param <T> is type of value this bind variable can hold
 */
public interface BindValueT<T> extends BindValue, ExpressionT<T> {

  /**
   * Type associated with this bind value.
   *
   * @return type associated with bind variable
   */
  @Override
  Class<T> getType();

  /**
   * Value assigned to bind variable.
   *
   * @return value assigned to bind variable, null if no value has been assigned
   */
  @Nullable T getValue();

  @Override
  BindValueT<T> withValue(@Nullable Object newValue);

  @Override
  BindValueT<T> combine(BindName other);
}
