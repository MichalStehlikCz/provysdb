package com.provys.db.sqlquery.literals;

import com.provys.common.datatype.DtDate;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralDtDateHandler implements SqlLiteralTypeHandler<DtDate> {

  private static final SqlLiteralDtDateHandler INSTANCE = new SqlLiteralDtDateHandler();
  private static final long serialVersionUID = -6338012492787869489L;

  /**
   * Instance of DtDate type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralDtDateHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralDtDateHandler() {
  }

  @Override
  public Class<DtDate> getType() {
    return DtDate.class;
  }

  @Override
  public String getLiteral(@Nullable DtDate value) {
    if (value == null) {
      return "TO_DATE(NULL)";
    }
    return "DATE'" + value.toIso() + '\'';
  }

  @Override
  public void appendLiteral(StringBuilder builder, @Nullable DtDate value) {
    if (value == null) {
      builder.append("TO_DATE(NULL)");
    } else {
      builder.append("DATE'").append(value.toIso()).append('\'');
    }
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralDtDateHandler{}";
  }
}
