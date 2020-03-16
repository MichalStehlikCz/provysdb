package com.provys.provysdb.sqldefault.expression;

import com.provys.provysdb.sql.CodeBuilder;

final class LiteralNVarchar extends LiteralBase<String> {

  /**
   * Get NVARCHAR2 literal corresponding to given String value.
   *
   * @param value is string value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralNVarchar of(String value) {
    return new LiteralNVarchar(value);
  }

  private LiteralNVarchar(String value) {
    super(value);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("N'").append(getValue().replace("'", "''")).append('\'');
  }

  @Override
  public Class<String> getType() {
    return String.class;
  }

  @Override
  public String toString() {
    return "LiteralNVarchar{" + super.toString() + '}';
  }
}
