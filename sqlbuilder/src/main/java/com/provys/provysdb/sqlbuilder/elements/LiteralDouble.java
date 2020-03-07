package com.provys.provysdb.sqlbuilder.elements;

final class LiteralDouble extends LiteralNumber<Double> {

  /**
   * Get literal corresponding to given Double value.
   *
   * @param value is double value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralDouble of(double value) {
    return new LiteralDouble(value);
  }

  private LiteralDouble(Double value) {
    super(value);
  }

  @Override
  public Class<Double> getType() {
    return Double.class;
  }

  @Override
  public String toString() {
    return "LiteralDouble{" + super.toString() + '}';
  }
}
