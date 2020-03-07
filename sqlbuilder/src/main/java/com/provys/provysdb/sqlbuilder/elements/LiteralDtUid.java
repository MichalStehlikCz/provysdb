package com.provys.provysdb.sqlbuilder.elements;

import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

final class LiteralDtUid extends LiteralBase<DtUid> {

  private static final LiteralDtUid PRIV = new LiteralDtUid(DtUid.PRIV);
  private static final LiteralDtUid ME = new LiteralDtUid(DtUid.ME);

  /**
   * Get literal corresponding to given DtUid value.
   *
   * @param value is Uid value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralDtUid of(DtUid value) {
    if (value.isPriv()) {
      return PRIV;
    }
    if (value.isME()) {
      return ME;
    }
    return new LiteralDtUid(value);
  }

  private LiteralDtUid(DtUid value) {
    super(value);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append(getValue().getValue().toString());
  }

  @Override
  public Class<DtUid> getType() {
    return DtUid.class;
  }

  @Override
  public String toString() {
    return "LiteralDtUid{" + super.toString() + '}';
  }
}
