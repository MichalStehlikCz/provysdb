package com.provys.db.sqlquery.literals;

import java.math.BigDecimal;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralBigDecimalHandler implements SqlLiteralTypeHandler<BigDecimal> {

  private static final SqlLiteralBigDecimalHandler INSTANCE = new SqlLiteralBigDecimalHandler();
  private static final long serialVersionUID = 8014175503771492033L;

  /**
   * Instance of BigDecimal literal handler.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralBigDecimalHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralBigDecimalHandler() {
  }

  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  @Override
  public String getLiteral(@Nullable BigDecimal value) {
    if (value == null) {
      return "TO_NUMBER(NULL)";
    }
    return value.toPlainString();
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralBigDecimalHandler{}";
  }
}
