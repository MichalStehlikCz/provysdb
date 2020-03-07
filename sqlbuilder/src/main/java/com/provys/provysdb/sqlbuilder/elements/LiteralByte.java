package com.provys.provysdb.sqlbuilder.elements;

final class LiteralByte extends LiteralNumber<Byte> {

  /**
   * Get literal corresponding to given Byte value.
   *
   * @param value is byte value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralByte of(byte value) {
    return new LiteralByte(value);
  }

  private LiteralByte(Byte value) {
    super(value);
  }

  @Override
  public Class<Byte> getType() {
    return Byte.class;
  }

  @Override
  public String toString() {
    return "LiteralByte{" + super.toString() + '}';
  }
}
