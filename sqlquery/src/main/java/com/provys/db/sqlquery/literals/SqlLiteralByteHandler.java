package com.provys.db.sqlquery.literals;

import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralByteHandler implements SqlLiteralTypeHandler<Byte> {

  private static final SqlLiteralByteHandler INSTANCE = new SqlLiteralByteHandler();
  private static final long serialVersionUID = 2417071738830548930L;

  /**
   * Instance of Byte type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralByteHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralByteHandler() {
  }

  @Override
  public Class<Byte> getType() {
    return Byte.class;
  }

  @Override
  public String getLiteral(@Nullable Byte value) {
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
    return "SqlLiteralByteHandler{}";
  }
}
