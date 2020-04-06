package com.provys.db.query.elements;

/**
 * Select query with single column of type T1.
 */
public interface SelectT1<T1> extends SelectT<SelectT1<T1>> {

  /**
   * Get first and only column of this select statement.
   *
   * @return first and only column of this select statement
   */
  SelectColumn<T1> getColumn1();

  /**
   * Type of first and only column of this statement.
   *
   * @return type of the first and only column of this statement
   */
  Class<T1> getType1();
}
