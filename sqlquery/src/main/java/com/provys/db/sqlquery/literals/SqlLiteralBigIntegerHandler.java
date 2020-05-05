package com.provys.db.sqlquery.literals;

import java.math.BigInteger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralBigIntegerHandler implements SqlLiteralTypeHandler<BigInteger> {

  private static final SqlLiteralBigIntegerHandler INSTANCE = new SqlLiteralBigIntegerHandler();
  private static final long serialVersionUID = -813713137221058115L;

  /**
   * Instance of BigInteger type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralBigIntegerHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralBigIntegerHandler() {
  }

  @Override
  public Class<BigInteger> getType() {
    return BigInteger.class;
  }

  @Override
  public String getLiteral(@Nullable BigInteger value) {
    if (value == null) {
      return "TO_NUMBER(NULL)";
    }
    return value.toString();
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralBigIntegerHandler{}";
  }
}
