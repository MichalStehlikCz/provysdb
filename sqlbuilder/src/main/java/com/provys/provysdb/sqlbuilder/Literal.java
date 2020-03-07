package com.provys.provysdb.sqlbuilder;

/**
 * Literal of specified type, special case of constant expression.
 *
 * @param <T> is type of literal's value
 */
public interface Literal<T> extends Expression<T> {

  /**
   * Value represented by literal.
   *
   * @return value this literal represents
   */
  T getValue();
}
