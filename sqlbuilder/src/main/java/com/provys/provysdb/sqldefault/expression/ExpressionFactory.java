package com.provys.provysdb.sqldefault.expression;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sql.Literal;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ExpressionFactory {

  private static final ExpressionFactory INSTANCE = new ExpressionFactory();

  /**
   * Singleton instance of expression factory.
   *
   * @return instance of expression factory
   */
  public static ExpressionFactory getInstance() {
    return INSTANCE;
  }

  /**
   * String literal.
   *
   * @param value is value of literal
   * @return varchar literal
   */
  public Literal<String> literal(String value) {
    return LiteralVarchar.of(value);
  }

  /**
   * Byte literal.
   *
   * @param value is value of literal
   * @return byte literal
   */
  public Literal<Byte> literal(byte value) {
    return LiteralByte.of(value);
  }

  /**
   * Short literal.
   *
   * @param value is value of literal
   * @return short literal
   */
  public Literal<Short> literal(short value) {
    return LiteralShort.of(value);
  }

  /**
   * Integer literal.
   *
   * @param value is value of literal
   * @return integer literal
   */
  public Literal<Integer> literal(int value) {
    return LiteralInt.of(value);
  }

  /**
   * Long literal.
   *
   * @param value is value of literal
   * @return long literal
   */
  public Literal<Long> literal(long value) {
    return LiteralLong.of(value);
  }

  /**
   * BigInteger literal.
   *
   * @param value is value of literal
   * @return BigInteger literal
   */
  public Literal<BigInteger> literal(BigInteger value) {
    return LiteralBigInteger.of(value);
  }

  /**
   * Float literal.
   *
   * @param value is value of literal
   * @return float literal
   */
  public Literal<Float> literal(float value) {
    return LiteralFloat.of(value);
  }

  /**
   * Double literal.
   *
   * @param value is value of literal
   * @return double literal
   */
  public Literal<Double> literal(double value) {
    return LiteralDouble.of(value);
  }

  /**
   * BigDecimal literal.
   *
   * @param value is value of literal
   * @return BigDecimal literal
   */
  public Literal<BigDecimal> literal(BigDecimal value) {
    return LiteralBigDecimal.of(value);
  }

  /**
   * Boolean literal.
   *
   * @param value is value of literal. Note that Java boolean is translated to Y/N char
   * @return boolean literal
   */
  public Literal<Boolean> literal(boolean value) {
    return LiteralBoolean.of(value);
  }

  /**
   * DtUid literal.
   *
   * @param value is value of literal
   * @return DtUid literal
   */
  public Literal<DtUid> literal(DtUid value) {
    return LiteralDtUid.of(value);
  }

  /**
   * DtDate literal.
   *
   * @param value is value of literal
   * @return date literal
   */
  public Literal<DtDate> literal(DtDate value) {
    return LiteralDate.of(value);
  }

  /**
   * DtDateTime literal.
   *
   * @param value is value of literal
   * @return datetime literal
   */
  public Literal<DtDateTime> literal(DtDateTime value) {
    return LiteralDateTime.of(value);
  }

  /**
   * String literal represented as NVARCHAR2.
   *
   * @param value is value of literal
   * @return NVarchar literal
   */
  public Literal<String> literalNVarchar(String value) {
    return LiteralNVarchar.of(value);
  }
}
