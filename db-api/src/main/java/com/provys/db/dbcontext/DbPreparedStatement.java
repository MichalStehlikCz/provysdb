package com.provys.db.dbcontext;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Provys extension of JDBC's prepared statement. Built on Provys connection, adds support for work
 * with Provys specific data-types via parametrised setValue method
 */
public interface DbPreparedStatement extends PreparedStatement, DbStatement {

  @Override
  DbResultSet executeQuery() throws SQLException;

  /**
   * Sql statement this prepared statement is built on.
   *
   * @return com.provys.db.sql statement this prepared statement is built on
   */
  String getSql();

  /**
   * Set (mandatory) value to bind value of database boolean type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullDbBoolean(int parameterIndex, boolean value);

  /**
   * Set optional value to bind value of database boolean type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableDbBoolean(int parameterIndex, @Nullable Boolean value);

  /**
   * Set (mandatory) value to bind value of provys boolean type (char Y / N / NULL).
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullBoolean(int parameterIndex, boolean value);

  /**
   * Set value to bind value of provys boolean type (char Y / N / NULL).
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableBoolean(int parameterIndex, @Nullable Boolean value);

  /**
   * Set (mandatory) value to bind value of byte type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullByte(int parameterIndex, byte value);

  /**
   * Set value to bind value of Byte type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableByte(int parameterIndex, @Nullable Byte value);

  /**
   * Set (mandatory) value to bind value of short type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullShort(int parameterIndex, short value);

  /**
   * Set value to bind value of Short type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableShort(int parameterIndex, @Nullable Short value);

  /**
   * Set (mandatory) value to bind value of int type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullInteger(int parameterIndex, int value);

  /**
   * Set value to bind value of Integer type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableInteger(int parameterIndex, @Nullable Integer value);

  /**
   * Set (mandatory) value to bind value of float type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullFloat(int parameterIndex, float value);

  /**
   * Set value to bind value of Float type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableFloat(int parameterIndex, @Nullable Float value);

  /**
   * Set (mandatory) value to bind value of double type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullDouble(int parameterIndex, double value);

  /**
   * Set value to bind value of Double type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableDouble(int parameterIndex, @Nullable Double value);

  /**
   * Set (mandatory) value to bind value of char type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullCharacter(int parameterIndex, char value);

  /**
   * Set value to bind value of Character type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableCharacter(int parameterIndex, @Nullable Character value);

  /**
   * Set (mandatory) value to bind value of String type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullString(int parameterIndex, String value);

  /**
   * Set value to bind value of String type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableString(int parameterIndex, @Nullable String value);

  /**
   * Set (mandatory) value to bind value of BigDecimal type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullBigDecimal(int parameterIndex, BigDecimal value);

  /**
   * Set value to bind value of BigDecimal type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableBigDecimal(int parameterIndex, @Nullable BigDecimal value);

  /**
   * Set (mandatory) value to bind value of BigInteger type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullBigInteger(int parameterIndex, BigInteger value);

  /**
   * Set value to bind value of BigInteger type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableBigInteger(int parameterIndex, @Nullable BigInteger value);

  /**
   * Set (mandatory) value to bind value of Provys Uid type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullDtUid(int parameterIndex, DtUid value);

  /**
   * Set (optional) value to bind value of Provys Uid type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableDtUid(int parameterIndex, @Nullable DtUid value);

  /**
   * Set (mandatory) value to bind value of Provys Date type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullDtDate(int parameterIndex, DtDate value);

  /**
   * Set (optional) value to bind value of Provys Date type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableDtDate(int parameterIndex, @Nullable DtDate value);

  /**
   * Set (mandatory) value to bind value of Provys DateTime type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNonNullDtDateTime(int parameterIndex, DtDateTime value);

  /**
   * Set (optional) value to bind value of Provys DateTime type.
   *
   * @param parameterIndex is index of bind variable
   * @param value          is value to be bound
   * @throws SqlException when any problem is encountered
   */
  void setNullableDtDateTime(int parameterIndex, @Nullable DtDateTime value);

  /**
   * Set mandatory value to bind value, use default conversion of supplied type to Sql. Note that
   * type will be inferred from value and thus might not correspond to formal parameter type
   *
   * @param parameterIndex is index of bind value in statement
   * @param value          is value to be bound
   */
  void setNonNullValue(int parameterIndex, Object value);

  /**
   * Set mandatory value to bind value, use default conversion of supplied type to Sql. Use supplied
   * type, not actual type retrieved in runtime from parameter value
   *
   * @param parameterIndex is index of bind value in statement
   * @param value          is value to be bound
   * @param type           is type of supplied value
   * @param <T>            is (Java) type of value to be bound
   */
  <T> void setNonNullValue(int parameterIndex, T value, Class<T> type);

  /**
   * Set optional value to bind value, use default conversion of supplied type to Sql.
   *
   * @param parameterIndex is index of bind value in statement
   * @param value          is value to be bound
   * @param type           is type of supplied value
   * @param <T>            is (Java) type of value to be bound
   */
  <T> void setNullableValue(int parameterIndex, @Nullable T value, Class<T> type);
}
