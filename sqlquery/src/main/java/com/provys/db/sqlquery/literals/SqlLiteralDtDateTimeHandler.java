package com.provys.db.sqlquery.literals;

import com.provys.common.datatype.DtDateTime;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlLiteralDtDateTimeHandler implements SqlLiteralTypeHandler<DtDateTime> {

  private static final SqlLiteralDtDateTimeHandler INSTANCE = new SqlLiteralDtDateTimeHandler();
  private static final long serialVersionUID = -8781987823830987170L;

  /**
   * Instance of DtDateTime type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlLiteralDtDateTimeHandler getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlLiteralDtDateTimeHandler() {
  }

  @Override
  public Class<DtDateTime> getType() {
    return DtDateTime.class;
  }

  @Override
  public String getLiteral(@Nullable DtDateTime value) {
    if (value == null) {
      return "TO_DATE(NULL)";
    }
    return "TO_DATE('" + value.toIso() + "', 'YYYY-MM-DD\"T\"HH24:MI:SS')";
  }

  @Override
  public void appendLiteral(StringBuilder builder, @Nullable DtDateTime value) {
    if (value == null) {
      builder.append("TO_DATE(NULL)");
    } else {
      builder.append("TO_DATE('").append(value.toIso()).append("', 'YYYY-MM-DD\"T\"HH24:MI:SS')");
    }
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlLiteralDtDateTimeHandler{}";
  }
}
