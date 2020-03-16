package com.provys.provysdb.sqldefault.expression;

final class LiteralFloat extends LiteralNumber<Float> {

  /**
   * Get literal corresponding to given Float value.
   *
   * @param value is float value this literal represents
   * @return new literal, representing supplied value
   */
  static LiteralFloat of(float value) {
    return new LiteralFloat(value);
  }

  private LiteralFloat(Float value) {
    super(value);
  }

  @Override
  public Class<Float> getType() {
    return Float.class;
  }

  @Override
  public String toString() {
    return "LiteralFloat{" + super.toString() + '}';
  }
}
