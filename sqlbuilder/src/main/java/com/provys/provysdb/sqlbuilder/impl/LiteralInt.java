package com.provys.provysdb.sqlbuilder.impl;

final class LiteralInt extends LiteralNumber<Integer> {

  /**
   * Get literal corresponding to given Integer value.
   *
   * @param value is integer value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralInt of(int value) {
    return new LiteralInt(value);
  }

  private LiteralInt(Integer value) {
    super(value);
  }

  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }

  @Override
  public String toString() {
    return "LiteralInt{" + super.toString() + '}';
  }
}
