package com.provys.db.query.elements;

public interface SelectT2<T1, T2> extends SelectT<SelectT2<T1, T2>> {

  /**
   * Get the first column of this select statement.
   *
   * @return the first column of this select statement
   */
  SelectColumn<T1> getColumn1();

  /**
   * Type of the first column of this statement.
   *
   * @return type of the first column of this statement
   */
  Class<T1> getType1();

  /**
   * Get the second column of this select statement.
   *
   * @return the second column of this select statement
   */
  SelectColumn<T2> getColumn2();

  /**
   * Type of the second column of this statement.
   *
   * @return type of the second column of this statement
   */
  Class<T2> getType2();
}
