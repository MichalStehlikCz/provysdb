package com.provys.provysdb.sqlbuilder.impl;

import java.math.BigInteger;

final class LiteralBigInteger extends LiteralNumber<BigInteger> {

  /**
   * Get literal corresponding to given Integer value.
   *
   * @param value is integer value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralBigInteger of(BigInteger value) {
    return new LiteralBigInteger(value);
  }

  private LiteralBigInteger(BigInteger value) {
    super(value);
  }

  @Override
  public Class<BigInteger> getType() {
    return BigInteger.class;
  }

  @Override
  public String toString() {
    return "LiteralBigInteger{" + super.toString() + '}';
  }
}
