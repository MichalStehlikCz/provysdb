package com.provys.provysdb.dbcontext;

import com.provys.common.datatype.DtUid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Adds support for Provys framework specific data-types to {@code ResultSet}. Moreover, exposes interface that is free
 * of SQLExceptions, as these are wrapped in SqlException - and it is runtime exception, thus doesn't have to be caught
 */
public interface DbResultSet extends ResultSet {

    /**
     * Return value of (mandatory) provys boolean column (char Y / N)
     *
     * @param columnIndex is index of column to be retrieved
     * @return boolean value corresponding to value in column
     */
    Boolean getNonnullBoolean(int columnIndex);

    /**
     * Return value of (mandatory) provys boolean column (char Y / N)
     *
     * @param columnLabel is name of column to be retrieved
     * @return boolean value corresponding to value in column
     */
    Boolean getNonnullBoolean(String columnLabel);

    /**
     * Return value of optional provys boolean column (char Y / N)
     *
     * @param columnIndex is index of column to be retrieved
     * @return boolean value corresponding to value in column, null if empty
     */
    @Nullable
    Boolean getNullableBoolean(int columnIndex);

    /**
     * Return value of optional provys boolean column (char Y / N)
     *
     * @param columnLabel is name of column to be retrieved
     * @return boolean value corresponding to value in column, null if empty
     */
    @Nullable
    Boolean getNullableBoolean(String columnLabel);

    /**
     * Return value of optional provys boolean column (char Y / N / NULL)
     *
     * @param columnIndex is index of column to be retrieved
     * @return boolean value corresponding to value in column
     */
    @Nonnull
    Optional<Boolean> getOptionalBoolean(int columnIndex);

    /**
     * Return value of optional provys boolean column (char Y / N / NULL)
     *
     * @param columnLabel is name of column to be retrieved
     * @return boolean value corresponding to value in column
     */
    @Nonnull
    Optional<Boolean> getOptionalBoolean(String columnLabel);

    /**
     * Return value of (mandatory) byte column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    byte getNonnullByte(int columnIndex);

    /**
     * Return value of (mandatory) byte column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    byte getNonnullByte(String columnLabel);

    /**
     * Return value of optional byte column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Byte getNullableByte(int columnIndex);

    /**
     * Return value of optional byte column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Byte getNullableByte(String columnLabel);

    /**
     * Return value of optional Byte column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Byte> getOptionalByte(int columnIndex);

    /**
     * Return value of optional Byte column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Byte> getOptionalByte(String columnLabel);

    /**
     * Return value of (mandatory) short column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    short getNonnullShort(int columnIndex);

    /**
     * Return value of (mandatory) short column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    short getNonnullShort(String columnLabel);

    /**
     * Return value of optional short column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Short getNullableShort(int columnIndex);

    /**
     * Return value of optional short column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Short getNullableShort(String columnLabel);

    /**
     * Return value of optional Short column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Short> getOptionalShort(int columnIndex);

    /**
     * Return value of optional Short column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Short> getOptionalShort(String columnLabel);

    /**
     * Return value of (mandatory) int column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    int getNonnullInteger(int columnIndex);

    /**
     * Return value of (mandatory) int column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    int getNonnullInteger(String columnLabel);

    /**
     * Return value of optional int column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Integer getNullableInteger(int columnIndex);

    /**
     * Return value of optional int column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Integer getNullableInteger(String columnLabel);

    /**
     * Return value of optional Integer column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Integer> getOptionalInteger(int columnIndex);

    /**
     * Return value of optional Integer column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Integer> getOptionalInteger(String columnLabel);

    /**
     * Return value of (mandatory) Character column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    char getNonnullCharacter(int columnIndex);

    /**
     * Return value of (mandatory) Character column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    char getNonnullCharacter(String columnLabel);

    /**
     * Return value of optional Character column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    Character getNullableCharacter(int columnIndex);

    /**
     * Return value of optional Character column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nullable
    Character getNullableCharacter(String columnLabel);

    /**
     * Return value of optional Character column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Character> getOptionalCharacter(int columnIndex);

    /**
     * Return value of optional Character column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<Character> getOptionalCharacter(String columnLabel);

    /**
     * Return value of (mandatory) String column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    String getNonnullString(int columnIndex);

    /**
     * Return value of (mandatory) String column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    String getNonnullString(String columnLabel);

    /**
     * Return value of optional String column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    String getNullableString(int columnIndex);

    /**
     * Return value of optional String column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nullable
    String getNullableString(String columnLabel);

    /**
     * Return value of optional String column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<String> getOptionalString(int columnIndex);

    /**
     * Return value of optional String column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<String> getOptionalString(String columnLabel);

    /**
     * Return value of (mandatory) BigDecimal column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    BigDecimal getNonnullBigDecimal(int columnIndex);

    /**
     * Return value of (mandatory) BigDecimal column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    BigDecimal getNonnullBigDecimal(String columnLabel);

    /**
     * Return value of optional BigDecimal column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    BigDecimal getNullableBigDecimal(int columnIndex);

    /**
     * Return value of optional BigDecimal column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    BigDecimal getNullableBigDecimal(String columnLabel);

    /**
     * Return value of optional BigDecimal column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<BigDecimal> getOptionalBigDecimal(int columnIndex);

    /**
     * Return value of optional BigDecimal column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<BigDecimal> getOptionalBigDecimal(String columnLabel);

    /**
     * Return value of (mandatory) Uid column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    DtUid getNonnullDtUid(int columnIndex);

    /**
     * Return value of (mandatory) Uid column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    DtUid getNonnullDtUid(String columnLabel);

    /**
     * Return value of optional Uid column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    DtUid getNullableDtUid(int columnIndex);

    /**
     * Return value of optional Uid column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column, null when empty
     */
    @Nullable
    DtUid getNullableDtUid(String columnLabel);

    /**
     * Return value of optional Uid column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<DtUid> getOptionalDtUid(int columnIndex);

    /**
     * Return value of optional Uid column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     */
    @Nonnull
    Optional<DtUid> getOptionalDtUid(String columnLabel);
}
