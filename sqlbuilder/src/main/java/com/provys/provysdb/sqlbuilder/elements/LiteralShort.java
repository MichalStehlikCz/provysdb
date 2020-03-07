package com.provys.provysdb.sqlbuilder.elements;

final class LiteralShort extends LiteralNumber<Short> {

  /**
   * Get literal corresponding to given Short value.
   *
   * @param value is short value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralShort of(short value) {
    return new LiteralShort(value);
  }

  private LiteralShort(Short value) {
    super(value);
  }

  @Override
  public Class<Short> getType() {
    return Short.class;
  }

  @Override
  public String toString() {
    return "LiteralShort{" + super.toString() + '}';
  }
}
