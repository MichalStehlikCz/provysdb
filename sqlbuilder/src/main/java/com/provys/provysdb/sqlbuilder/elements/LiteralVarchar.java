package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

/**
 * Class represents SQL string literal (VARCHAR2).
 */
final class LiteralVarchar extends LiteralBase<String> {

  /**
   * Get literal corresponding to given String value.
   *
   * @param value is string value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralVarchar of(String value) {
    return new LiteralVarchar(value);
  }

  private LiteralVarchar(String value) {
    super(value);
  }

  @Override
  public void appendExpression(CodeBuilder builder) {
    builder.append('\'')
        .append(getValue()
            .replace("'", "''")
            .replace("\n", "' || CHR(10) || '"))
        .append('\'');
  }

  @Override
  public Class<String> getType() {
    return String.class;
  }

  @Override
  public String toString() {
    return "LiteralVarchar{" + super.toString() + '}';
  }
}
