package com.provys.db.sqlquery.literals;

import com.provys.common.datatype.DtBoolean;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralBooleanHandler implements SqlLiteralTypeHandler<Boolean> {

  private static final String LITERAL_TRUE = '\'' + DtBoolean.toProvysDb(true) + '\'';
  private static final String LITERAL_FALSE = '\'' + DtBoolean.toProvysDb(false) + '\'';

  private static final SqlLiteralBooleanHandler INSTANCE = new SqlLiteralBooleanHandler();
  private static final long serialVersionUID = 3273938096409104189L;

  /**
   * Instance of boolean type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralBooleanHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralBooleanHandler() {
  }

  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  @Override
  public String getLiteral(@Nullable Boolean value) {
    if (value == null) {
      return "NULL";
    }
    return value ? LITERAL_TRUE : LITERAL_FALSE;
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralBooleanHandler{}";
  }
}
