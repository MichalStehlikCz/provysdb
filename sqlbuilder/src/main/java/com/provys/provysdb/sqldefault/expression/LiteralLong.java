package com.provys.provysdb.sqldefault.expression;

final class LiteralLong extends LiteralNumber<Long> {

  /**
   * Get literal corresponding to given Long value.
   *
   * @param value is long value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralLong of(long value) {
    return new LiteralLong(value);
  }

  private LiteralLong(Long value) {
    super(value);
  }

  @Override
  public Class<Long> getType() {
    return Long.class;
  }

  @Override
  public String toString() {
    return "LiteralLong{" + super.toString() + '}';
  }
}
