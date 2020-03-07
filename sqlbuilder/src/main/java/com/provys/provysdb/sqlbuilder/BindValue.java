package com.provys.provysdb.sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Bind value connects type and value to bind name.
 *
 * @param <T> is type of value this bind variable can hold
 */
public interface BindValue<T> extends Expression<T> {

  /**
   * Bind name.
   *
   * @return bind name
   */
  BindName getBindName();

  /**
   * Bind name as string.
   *
   * @return bind name as string
   */
  String getName();

  /**
   * Value assigned to bind variable.
   *
   * @return value assigned to bind variable, null if no value has been assigned
   */
  @Nullable T getValue();

  /**
   * Get value assigned to bind variable. This variant does runtime type conversion and bypasses
   * compile time type checking; typed variant of this method should be used when possible
   *
   * @param returnType is type parameter should have
   * @param <U>  is type parameter, representing type of returned value
   * @return value associated with this bind value, empty optional if no value has been defined yet
   */
  <U> @Nullable U getValue(Class<U> returnType);

  /**
   * Limits bind value to specified type. If current value is not compatible with required type,
   * fails. Type must be specialisation of current type.
   *
   * @param newType is required new type
   * @param <U> is type parameter representing new type
   * @return bind value with clarified type
   */
  <U extends T> BindValue<U> type(Class<U> newType);

  /**
   * Return bind value with the same name and type, but with new value. Used as part of bind value
   * method
   *
   * @param newValue is value to be assigned to new bind
   * @return bind variable with the same name as old one, but with the new value
   */
  BindValue<T> value(@Nullable T newValue);

  /**
   * Method can be used when constructing statement and merging its parts. It combines two bind
   * values; they should have the same name and type. It verifies their values; if they have
   * different non-null values, exception is raised. Otherwise it uses one of variables to be
   * combined, preferring one with the value
   *
   * @param other is bind variable this variable should be combined with
   * @return this or other bind variable, depending which has more complete information
   */
  BindValue<T> combine(BindValue<?> other);
}
