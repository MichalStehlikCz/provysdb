package com.provys.provysdb.sqlbuilder;

/**
 * Literal of specified type, special case of constant expression. Is specialized based on type,
 * for types without specialisation, OtherLiteral is used.
 *
 * @param <T> is type of literal's value
 * @param <E> is actual type of literal interface, preserved during cloning
 */
public interface Literal<T, E extends Literal<T, E>> extends Expression<T, E> {

  /**
   * Value represented by literal.
   *
   * @return value this literal represents
   */
  T getValue();
}