package com.provys.provysdb.sqldefault.expression;

import com.provys.common.datatype.DtDateTime;
import com.provys.provysdb.sql.CodeBuilder;

/**
 * Class represents SQL datetime literal (maps to DATE).
 */
final class LiteralDateTime extends LiteralBase<DtDateTime> {

  /**
   * Create date literal from supplied date value.
   *
   * @param value is supplied date value
   * @return date literal, representing supplied value
   */
  static LiteralDateTime of(DtDateTime value) {
    return new LiteralDateTime(value);
  }

  private LiteralDateTime(DtDateTime value) {
    super(value);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("TO_DATE('").append(getValue().toIso())
        .append("', 'YYYY-MM-DD\"T\"HH24:MI:SS')");
  }

  @Override
  public Class<DtDateTime> getType() {
    return DtDateTime.class;
  }

  @Override
  public String toString() {
    return "LiteralDateTime{" + super.toString() + '}';
  }
}
