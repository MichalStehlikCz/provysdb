package com.provys.db.sqlquery.literals;

import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralStringHandler implements SqlLiteralTypeHandler<String> {

  private static final SqlLiteralStringHandler INSTANCE = new SqlLiteralStringHandler();
  private static final long serialVersionUID = -4502452502449053875L;

  /**
   * Instance of String type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralStringHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralStringHandler() {
  }

  @Override
  public Class<String> getType() {
    return String.class;
  }

  @Override
  public String getLiteral(@Nullable String value) {
    if (value == null) {
      return "NULL";
    }
    return '\'' + value
        .replace("'", "''")
        .replace("\n", "'||CHR(10)||'") + '\'';
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralStringHandler{}";
  }
}
