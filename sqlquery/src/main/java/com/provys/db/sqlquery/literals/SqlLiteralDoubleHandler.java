package com.provys.db.sqlquery.literals;

import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralDoubleHandler implements SqlLiteralTypeHandler<Double> {

  private static final SqlLiteralDoubleHandler INSTANCE = new SqlLiteralDoubleHandler();
  private static final long serialVersionUID = -3728445812106780060L;

  /**
   * Instance of Double type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralDoubleHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralDoubleHandler() {
  }

  @Override
  public Class<Double> getType() {
    return Double.class;
  }

  @Override
  public String getLiteral(@Nullable Double value) {
    return (value == null) ? "TO_NUMBER(NULL)" : value.toString();
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralDoubleHandler{}";
  }
}
