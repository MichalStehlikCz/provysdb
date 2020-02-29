package com.provys.provysdb.sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Bind value extends {@link BindName} by connecting type and potentially value to named bind.
 */
public interface BindValue extends BindName {

  /**
   * Type associated with bind value.
   *
   * @return type associated with bind value
   */
  Class<?> getType();

  /**
   * Get value assigned to bind variable. This variant does runtime type conversion and thus is
   * unsafe. Typed variant of this method (defined in BindVariableT interface) should be used
   * whenever possible
   *
   * @param returnType is type parameter should have
   * @param <T>  is type parameter, representing type of returned value
   * @return value associated with this bind value, empty optional if no value has been defined yet
   */
  <T> @Nullable T getValue(Class<T> returnType);

  /**
   * Method can be used when constructing statement and merging its parts. It combines two bind
   * values; they should have the same name and type. It verifies their values; if they have
   * different non-null values, exception is raised. Otherwise it uses one of variables to be
   * combined, preferring one with the value
   *
   * @param other is bind variable this variable should be combined with
   * @return this or other bind variable, depending which has more complete information
   */
  @Override
  BindValue combine(BindName other);
}
