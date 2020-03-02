package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

/**
 * Class represents SQL date literal (maps to DATE).
 */
final class LiteralDate extends LiteralBase<DtDate> {

  /**
   * Create date literal from supplied date value.
   *
   * @param value is supplied date value
   * @return date literal, representing supplied value
   */
  static LiteralDate of(DtDate value) {
    return new LiteralDate(value);
  }

  private LiteralDate(DtDate value) {
    super(value);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("DATE'").append(getValue().toIso()).append("'");
  }

  @Override
  public Class<DtDate> getType() {
    return DtDate.class;
  }

  @Override
  public String toString() {
    return "LiteralDate{" + super.toString() + '}';
  }
}
