package com.provys.provysdb.sqlbuilder;

/**
 * Single wrapper around String, represents name of bind variable and implements appropriate
 * validation.
 */
public interface BindName {

  /**
   * Bind name.
   *
   * @return bind name
   */
  String getName();
}
