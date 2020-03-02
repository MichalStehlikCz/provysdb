package com.provys.provysdb.sqlbuilder.impl;

import java.math.BigDecimal;

final class LiteralBigDecimal extends LiteralNumber<BigDecimal> {

  /**
   * Get literal corresponding to given BigDecimal value.
   *
   * @param value is value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralBigDecimal of(BigDecimal value) {
    return new LiteralBigDecimal(value);
  }

  private LiteralBigDecimal(BigDecimal value) {
    super(value);
  }

  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  @Override
  public String toString() {
    return "LiteralBigDecimal{" + super.toString() + '}';
  }
}
