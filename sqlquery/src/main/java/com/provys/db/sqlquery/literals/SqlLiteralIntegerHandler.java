package com.provys.db.sqlquery.literals;

import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralIntegerHandler implements SqlLiteralTypeHandler<Integer> {

  private static final SqlLiteralIntegerHandler INSTANCE = new SqlLiteralIntegerHandler();
  private static final long serialVersionUID = -2075005058683428885L;

  /**
   * Instance of Integer type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralIntegerHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralIntegerHandler() {
  }

  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }

  @Override
  public String getLiteral(@Nullable Integer value) {
    return (value == null) ? "TO_NUMBER(NULL)" : value.toString();
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralIntegerHandler{}";
  }
}
