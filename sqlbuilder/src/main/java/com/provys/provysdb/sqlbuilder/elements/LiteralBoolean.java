package com.provys.provysdb.sqlbuilder.elements;

import com.provys.common.datatype.DtBoolean;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

final class LiteralBoolean extends LiteralBase<Boolean> {

  private static final LiteralBoolean TRUE = new LiteralBoolean(true);
  private static final LiteralBoolean FALSE = new LiteralBoolean(false);

  static LiteralBoolean of(boolean value) {
    return value ? TRUE : FALSE;
  }

  private LiteralBoolean(Boolean value) {
    super(value);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('\'').append(DtBoolean.toProvysDb(getValue())).append('\'');
  }

  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  @Override
  public String toString() {
    return "LiteralBoolean{" + super.toString() + '}';
  }
}
