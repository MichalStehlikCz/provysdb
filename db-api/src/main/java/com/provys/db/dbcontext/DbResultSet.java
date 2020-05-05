package com.provys.db.dbcontext;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Adds support for Provys framework specific data-types to {@code ResultSet}. Moreover, exposes
 * interface that is free of SQLExceptions, as these are wrapped in SqlException - and it is runtime
 * exception, thus doesn't have to be caught
 */
public interface DbResultSet extends ResultSet {

  /**
   * Return value of (mandatory) provys boolean column (char Y / N).
   *
   * @param columnIndex is index of column to be retrieved
   * @return boolean value corresponding to value in column
   */
  Boolean getNonNullBoolean(int columnIndex);

  /**
   * Return value of (mandatory) provys boolean column (char Y / N).
   *
   * @param columnLabel is name of column to be retrieved
   * @return boolean value corresponding to value in column
   */
  Boolean getNonNullBoolean(String columnLabel);

  /**
   * Return value of optional provys boolean column (char Y / N).
   *
   * @param columnIndex is index of column to be retrieved
   * @return boolean value corresponding to value in column, null if empty
   */
  @Nullable Boolean getNullableBoolean(int columnIndex);

  /**
   * Return value of optional provys boolean column (char Y / N).
   *
   * @param columnLabel is name of column to be retrieved
   * @return boolean value corresponding to value in column, null if empty
   */
  @Nullable Boolean getNullableBoolean(String columnLabel);

  /**
   * Return value of optional provys boolean column (char Y / N / NULL).
   *
   * @param columnIndex is index of column to be retrieved
   * @return boolean value corresponding to value in column
   */
  Optional<Boolean> getOptionalBoolean(int columnIndex);

  /**
   * Return value of optional provys boolean column (char Y / N / NULL).
   *
   * @param columnLabel is name of column to be retrieved
   * @return boolean value corresponding to value in column
   */
  Optional<Boolean> getOptionalBoolean(String columnLabel);

  /**
   * Return value of (mandatory) byte column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  byte getNonNullByte(int columnIndex);

  /**
   * Return value of (mandatory) byte column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  byte getNonNullByte(String columnLabel);

  /**
   * Return value of optional byte column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Byte getNullableByte(int columnIndex);

  /**
   * Return value of optional byte column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Byte getNullableByte(String columnLabel);

  /**
   * Return value of optional Byte column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<Byte> getOptionalByte(int columnIndex);

  /**
   * Return value of optional Byte column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<Byte> getOptionalByte(String columnLabel);

  /**
   * Return value of (mandatory) short column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  short getNonNullShort(int columnIndex);

  /**
   * Return value of (mandatory) short column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  short getNonNullShort(String columnLabel);

  /**
   * Return value of optional short column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Short getNullableShort(int columnIndex);

  /**
   * Return value of optional short column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Short getNullableShort(String columnLabel);

  /**
   * Return value of optional Short column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<Short> getOptionalShort(int columnIndex);

  /**
   * Return value of optional Short column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<Short> getOptionalShort(String columnLabel);

  /**
   * Return value of (mandatory) int column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  int getNonNullInteger(int columnIndex);

  /**
   * Return value of (mandatory) int column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  int getNonNullInteger(String columnLabel);

  /**
   * Return value of optional int column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Integer getNullableInteger(int columnIndex);

  /**
   * Return value of optional int column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Integer getNullableInteger(String columnLabel);

  /**
   * Return value of optional Integer column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<Integer> getOptionalInteger(int columnIndex);

  /**
   * Return value of optional Integer column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<Integer> getOptionalInteger(String columnLabel);

  /**
   * Return value of (mandatory) float column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  float getNonNullFloat(int columnIndex);

  /**
   * Return value of (mandatory) float column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  float getNonNullFloat(String columnLabel);

  /**
   * Return value of optional Float column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Float getNullableFloat(int columnIndex);

  /**
   * Return value of optional Float column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Float getNullableFloat(String columnLabel);

  /**
   * Return value of optional Float column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<Float> getOptionalFloat(int columnIndex);

  /**
   * Return value of optional Float column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<Float> getOptionalFloat(String columnLabel);

  /**
   * Return value of (mandatory) double column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  double getNonNullDouble(int columnIndex);

  /**
   * Return value of (mandatory) double column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  double getNonNullDouble(String columnLabel);

  /**
   * Return value of optional Double column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Double getNullableDouble(int columnIndex);

  /**
   * Return value of optional Double column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Double getNullableDouble(String columnLabel);

  /**
   * Return value of optional Double column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<Double> getOptionalDouble(int columnIndex);

  /**
   * Return value of optional Double column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<Double> getOptionalDouble(String columnLabel);

  /**
   * Return value of (mandatory) Character column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  char getNonNullCharacter(int columnIndex);

  /**
   * Return value of (mandatory) Character column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  char getNonNullCharacter(String columnLabel);

  /**
   * Return value of optional Character column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable Character getNullableCharacter(int columnIndex);

  /**
   * Return value of optional Character column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  @Nullable Character getNullableCharacter(String columnLabel);

  /**
   * Return value of optional Character column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<Character> getOptionalCharacter(int columnIndex);

  /**
   * Return value of optional Character column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<Character> getOptionalCharacter(String columnLabel);

  /**
   * Return value of (mandatory) String column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  String getNonNullString(int columnIndex);

  /**
   * Return value of (mandatory) String column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  String getNonNullString(String columnLabel);

  /**
   * Return value of optional String column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable String getNullableString(int columnIndex);

  /**
   * Return value of optional String column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  @Nullable String getNullableString(String columnLabel);

  /**
   * Return value of optional String column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<String> getOptionalString(int columnIndex);

  /**
   * Return value of optional String column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<String> getOptionalString(String columnLabel);

  /**
   * Return value of (mandatory) BigDecimal column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  BigDecimal getNonNullBigDecimal(int columnIndex);

  /**
   * Return value of (mandatory) BigDecimal column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  BigDecimal getNonNullBigDecimal(String columnLabel);

  /**
   * Return value of optional BigDecimal column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable BigDecimal getNullableBigDecimal(int columnIndex);

  /**
   * Return value of optional BigDecimal column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable BigDecimal getNullableBigDecimal(String columnLabel);

  /**
   * Return value of optional BigDecimal column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<BigDecimal> getOptionalBigDecimal(int columnIndex);

  /**
   * Return value of optional BigDecimal column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<BigDecimal> getOptionalBigDecimal(String columnLabel);

  /**
   * Return value of (mandatory) BigInteger column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  BigInteger getNonNullBigInteger(int columnIndex);

  /**
   * Return value of (mandatory) BigInteger column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  BigInteger getNonNullBigInteger(String columnLabel);

  /**
   * Return value of optional BigInteger column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable BigInteger getNullableBigInteger(int columnIndex);

  /**
   * Return value of optional BigInteger column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable BigInteger getNullableBigInteger(String columnLabel);

  /**
   * Return value of optional BigInteger column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<BigInteger> getOptionalBigInteger(int columnIndex);

  /**
   * Return value of optional BigInteger column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<BigInteger> getOptionalBigInteger(String columnLabel);

  /**
   * Return value of (mandatory) Uid column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  DtUid getNonNullDtUid(int columnIndex);

  /**
   * Return value of (mandatory) Uid column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  DtUid getNonNullDtUid(String columnLabel);

  /**
   * Return value of optional Uid column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable DtUid getNullableDtUid(int columnIndex);

  /**
   * Return value of optional Uid column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable DtUid getNullableDtUid(String columnLabel);

  /**
   * Return value of optional Uid column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<DtUid> getOptionalDtUid(int columnIndex);

  /**
   * Return value of optional Uid column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<DtUid> getOptionalDtUid(String columnLabel);

  /**
   * Return value of (mandatory) Date column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  DtDate getNonNullDtDate(int columnIndex);

  /**
   * Return value of (mandatory) Date column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  DtDate getNonNullDtDate(String columnLabel);

  /**
   * Return value of optional Date column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable DtDate getNullableDtDate(int columnIndex);

  /**
   * Return value of optional Date column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable DtDate getNullableDtDate(String columnLabel);

  /**
   * Return value of optional Date column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<DtDate> getOptionalDtDate(int columnIndex);

  /**
   * Return value of optional Date column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<DtDate> getOptionalDtDate(String columnLabel);

  /**
   * Return value of (mandatory) DateTime column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  DtDateTime getNonNullDtDateTime(int columnIndex);

  /**
   * Return value of (mandatory) DateTime column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  DtDateTime getNonNullDtDateTime(String columnLabel);

  /**
   * Return value of optional DateTime column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable DtDateTime getNullableDtDateTime(int columnIndex);

  /**
   * Return value of optional DateTime column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column, null when empty
   */
  @Nullable DtDateTime getNullableDtDateTime(String columnLabel);

  /**
   * Return value of optional DateTime column.
   *
   * @param columnIndex is index of column to be retrieved
   * @return value in column
   */
  Optional<DtDateTime> getOptionalDtDateTime(int columnIndex);

  /**
   * Return value of optional DateTime column.
   *
   * @param columnLabel is name of column to be retrieved
   * @return value in column
   */
  Optional<DtDateTime> getOptionalDtDateTime(String columnLabel);

  /**
   * Return value of (mandatory) column of specified type.
   *
   * @param columnIndex is index of column to be retrieved
   * @param type        is type of value to be read
   * @param <T>         is type of column
   * @return value in column
   */
  <T> @NonNull T getNonNullValue(int columnIndex, Class<T> type);

  /**
   * Return value of (mandatory) column of specified type.
   *
   * @param columnLabel is name of column to be retrieved
   * @param type        is type of value to be read
   * @param <T>         is type of column
   * @return value in column
   */
  <T> @NonNull T getNonNullValue(String columnLabel, Class<T> type);

  /**
   * Return value of optional column of specified type.
   *
   * @param columnIndex is index of column to be retrieved
   * @param type        is type of value to be read
   * @param <T>         is type of column
   * @return value in column, null when empty
   */
  <T> @Nullable T getNullableValue(int columnIndex, Class<T> type);

  /**
   * Return value of optional column of specified type.
   *
   * @param columnLabel is name of column to be retrieved
   * @param type        is type of value to be read
   * @param <T>         is type of column
   * @return value in column, null when empty
   */
  <T> @Nullable T getNullableValue(String columnLabel, Class<T> type);

  /**
   * Return value of optional column of specified type.
   *
   * @param columnIndex is index of column to be retrieved
   * @param type        is type of value to be read
   * @param <T>         is type of column
   * @return value in column
   */
  <T> Optional<@NonNull T> getOptionalValue(int columnIndex, Class<T> type);

  /**
   * Return value of optional column of specified type.
   *
   * @param columnLabel is name of column to be retrieved
   * @param type        is type of value to be read
   * @param <T>         is type of column
   * @return value in column
   */
  <T> Optional<@NonNull T> getOptionalValue(String columnLabel, Class<T> type);
}
