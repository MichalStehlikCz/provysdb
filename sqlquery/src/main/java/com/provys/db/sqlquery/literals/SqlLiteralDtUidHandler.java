package com.provys.db.sqlquery.literals;

import com.provys.common.datatype.DtUid;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralDtUidHandler implements SqlLiteralTypeHandler<DtUid> {

  private static final SqlLiteralDtUidHandler INSTANCE = new SqlLiteralDtUidHandler();
  private static final long serialVersionUID = 8575377433387689196L;

  /**
   * Instance of DtUid type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralDtUidHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralDtUidHandler() {
  }

  @Override
  public Class<DtUid> getType() {
    return DtUid.class;
  }

  @Override
  public String getLiteral(@Nullable DtUid value) {
    if (value == null) {
      return "TO_NUMBER(NULL)";
    }
    return value.getValue().toString();
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralDtUidHandler{}";
  }
}
