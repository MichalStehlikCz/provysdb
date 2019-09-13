package com.provys.provysdb.dbcontext;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Adds support for Provys framework specific datatypes to  {@code ResultSet}
 */
public interface ProvysResultSet extends ResultSet {

    /**
     * Return value of (mandatory) provys boolean column (char Y / N)
     *
     * @param columnIndex is index of column to be retrieved
     * @return boolean value corresponding to value in column
     * @throws SQLException when any Sql error occurs
     */
    boolean getNonnullDtBoolean(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) provys boolean column (char Y / N)
     *
     * @param columnLabel is name of column to be retrieved
     * @return boolean value corresponding to value in column
     * @throws SQLException when any Sql error occurs
     */
    boolean getNonnullDtBoolean(String columnLabel) throws SQLException;

    /**
     * Return value of optional provys boolean column (char Y / N / NULL)
     *
     * @param columnIndex is index of column to be retrieved
     * @return boolean value corresponding to value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Boolean> getOptionalDtBoolean(int columnIndex) throws SQLException;

    /**
     * Return value of optional provys boolean column (char Y / N / NULL)
     *
     * @param columnLabel is name of column to be retrieved
     * @return boolean value corresponding to value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Boolean> getOptionalDtBoolean(String columnLabel) throws SQLException;

    /**
     * Return value of (mandatory) byte column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    byte getNonnullByte(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) byte column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    byte getNonnullByte(String columnLabel) throws SQLException;

    /**
     * Return value of optional Byte column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Byte> getOptionalByte(int columnIndex) throws SQLException;

    /**
     * Return value of optional Byte column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Byte> getOptionalByte(String columnLabel) throws SQLException;

    /**
     * Return value of (mandatory) short column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    short getNonnullShort(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) short column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    short getNonnullShort(String columnLabel) throws SQLException;

    /**
     * Return value of optional Short column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Short> getOptionalShort(int columnIndex) throws SQLException;

    /**
     * Return value of optional Short column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Short> getOptionalShort(String columnLabel) throws SQLException;

    /**
     * Return value of (mandatory) int column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    int getNonnullInteger(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) int column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    int getNonnullInteger(String columnLabel) throws SQLException;

    /**
     * Return value of optional Integer column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Integer> getOptionalInteger(int columnIndex) throws SQLException;

    /**
     * Return value of optional Integer column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<Integer> getOptionalInteger(String columnLabel) throws SQLException;

    /**
     * Return value of (mandatory) String column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    String getNonnullString(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) String column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    String getNonnullString(String columnLabel) throws SQLException;

    /**
     * Return value of optional String column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<String> getOptionalString(int columnIndex) throws SQLException;

    /**
     * Return value of optional String column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<String> getOptionalString(String columnLabel) throws SQLException;

    /**
     * Return value of (mandatory) Uid column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    BigInteger getNonnullDtUid(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) Uid column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    BigInteger getNonnullDtUid(String columnLabel) throws SQLException;

    /**
     * Return value of optional Uid column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<BigInteger> getOptionalDtUid(int columnIndex) throws SQLException;

    /**
     * Return value of optional Uid column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<BigInteger> getOptionalDtUid(String columnLabel) throws SQLException;

    /**
     * Return value of (mandatory) BigDecimal column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    BigDecimal getNonnullBigDecimal(int columnIndex) throws SQLException;

    /**
     * Return value of (mandatory) BigDecimal column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    BigDecimal getNonnullBigDecimal(String columnLabel) throws SQLException;

    /**
     * Return value of optional BigDecimal column
     *
     * @param columnIndex is index of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<BigDecimal> getOptionalBigDecimal(int columnIndex) throws SQLException;

    /**
     * Return value of optional BigDecimal column
     *
     * @param columnLabel is name of column to be retrieved
     * @return value in column
     * @throws SQLException when any Sql error occurs
     */
    @Nonnull
    Optional<BigDecimal> getOptionalBigDecimal(String columnLabel) throws SQLException;
}
