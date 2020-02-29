package com.provys.provysdb.sqlbuilder;

/**
 * Single wrapper around String, represents name of bind variable and implements appropriate
 * validation.
 */
public interface BindName extends Expression {

  /**
   * Bind name.
   *
   * @return bind name
   */
  String getName();

  /**
   * Method can be used when constructing statement and merging its parts. It combines two bind
   * values; they should have the same name and type. It verifies their values; if they have
   * different non-null values, exception is raised. Otherwise it uses one of variables to be
   * combined, preferring one with the value
   *
   * @param other is bind variable this variable should be combined with
   * @return this or other bind variable, depending which has more complete information
   */
  BindName combine(BindName other);
}
