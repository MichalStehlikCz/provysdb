package com.provys.db.sqldb.dbcontext;

import com.provys.common.datatype.DtBoolean;
import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlException;
import com.provys.db.dbcontext.SqlTypeMap;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Provys specialisation of {@link PreparedStatement}. Adds handling of provys specific data types.
 *
 * @param <T> is wrapped class type; used to support child classes that require specialization of
 *            PreparedStatement as wrapped type.
 */
public class DefaultPreparedStatement<T extends PreparedStatement> extends DefaultStatement<T>
    implements DbPreparedStatement {

  private final String sql;

  /**
   * Created Provys prepared statement as wrapper around Jdbc prepared statement. Published to allow
   * subclassing.
   *
   * @param sql is statement supplied to wrapped prepared statement
   * @param preparedStatement is wrapped prepared statement
   * @param sqlTypeMap is type map used to bind variables in this database context
   */
  protected DefaultPreparedStatement(String sql, T preparedStatement, SqlTypeMap sqlTypeMap) {
    super(preparedStatement, sqlTypeMap);
    this.sql = sql;
  }

  /**
   * Value of field sql.
   *
   * @return value of field sql
   */
  @Override
  public String getSql() {
    return sql;
  }

  @Override
  public DbResultSet executeQuery() throws SQLException {
    return new DefaultResultSet(getStatement().executeQuery(), getSqlTypeMap());
  }

  @Override
  public int executeUpdate() throws SQLException {
    return getStatement().executeUpdate();
  }

  @Override
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    getStatement().setNull(parameterIndex, sqlType);
  }

  @Override
  public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
    getStatement().setNull(parameterIndex, sqlType, typeName);
  }

  @Override
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    getStatement().setBoolean(parameterIndex, x);
  }

  @Override
  public void setByte(int parameterIndex, byte x) throws SQLException {
    getStatement().setByte(parameterIndex, x);
  }

  @Override
  public void setShort(int parameterIndex, short x) throws SQLException {
    getStatement().setShort(parameterIndex, x);
  }

  @Override
  public void setInt(int parameterIndex, int x) throws SQLException {
    getStatement().setInt(parameterIndex, x);
  }

  @Override
  public void setLong(int parameterIndex, long x) throws SQLException {
    getStatement().setLong(parameterIndex, x);
  }

  @Override
  public void setFloat(int parameterIndex, float x) throws SQLException {
    getStatement().setFloat(parameterIndex, x);
  }

  @Override
  public void setDouble(int parameterIndex, double x) throws SQLException {
    getStatement().setDouble(parameterIndex, x);
  }

  @Override
  public void setBigDecimal(int parameterIndex, @Nullable BigDecimal x) throws SQLException {
    getStatement().setBigDecimal(parameterIndex, x);
  }

  @Override
  public void setString(int parameterIndex, @Nullable String x) throws SQLException {
    getStatement().setString(parameterIndex, x);
  }

  @Override
  @SuppressWarnings("Nullness") // fixes incorrect annotation in checker JDK
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    getStatement().setBytes(parameterIndex, x);
  }

  @Override
  public void setDate(int parameterIndex, @Nullable Date x) throws SQLException {
    getStatement().setDate(parameterIndex, x);
  }

  @Override
  public void setDate(int parameterIndex, @Nullable Date x, Calendar cal) throws SQLException {
    getStatement().setDate(parameterIndex, x, cal);
  }

  @Override
  public void setTime(int parameterIndex, @Nullable Time x) throws SQLException {
    getStatement().setTime(parameterIndex, x);
  }

  @Override
  public void setTime(int parameterIndex, @Nullable Time x, Calendar cal) throws SQLException {
    getStatement().setTime(parameterIndex, x, cal);
  }

  @Override
  public void setTimestamp(int parameterIndex, @Nullable Timestamp x) throws SQLException {
    getStatement().setTimestamp(parameterIndex, x);
  }

  @Override
  public void setTimestamp(int parameterIndex, @Nullable Timestamp x, Calendar cal)
      throws SQLException {
    getStatement().setTimestamp(parameterIndex, x, cal);
  }

  @Override
  @Deprecated(since = "1.2")
  @SuppressWarnings({"squid:MissingDeprecatedCheck", "squid:S1133"})
  public void setUnicodeStream(int parameterIndex, @Nullable InputStream x, int length)
      throws SQLException {
    getStatement().setUnicodeStream(parameterIndex, x, length);
  }

  @Override
  public void clearParameters() throws SQLException {
    getStatement().clearParameters();
  }

  @Override
  public boolean execute() throws SQLException {
    return getStatement().execute();
  }

  @Override
  public void addBatch() throws SQLException {
    getStatement().addBatch();
  }

  @Override
  public void setRef(int parameterIndex, Ref x) throws SQLException {
    getStatement().setRef(parameterIndex, x);
  }

  @Override
  public void setBlob(int parameterIndex, @Nullable Blob x) throws SQLException {
    getStatement().setBlob(parameterIndex, x);
  }

  @Override
  public void setBlob(int parameterIndex, @Nullable InputStream inputStream) throws SQLException {
    getStatement().setBlob(parameterIndex, inputStream);
  }

  @Override
  public void setBlob(int parameterIndex, @Nullable InputStream inputStream, long length)
      throws SQLException {
    getStatement().setBlob(parameterIndex, inputStream, length);
  }

  @Override
  public void setClob(int parameterIndex, @Nullable Clob x) throws SQLException {
    getStatement().setClob(parameterIndex, x);
  }

  @Override
  public void setClob(int parameterIndex, @Nullable Reader reader) throws SQLException {
    getStatement().setClob(parameterIndex, reader);
  }

  @Override
  public void setClob(int parameterIndex, @Nullable Reader reader, long length)
      throws SQLException {
    getStatement().setClob(parameterIndex, reader, length);
  }

  @Override
  public void setArray(int parameterIndex, Array x) throws SQLException {
    getStatement().setArray(parameterIndex, x);
  }

  @Override
  public @Nullable ResultSetMetaData getMetaData() throws SQLException {
    return getStatement().getMetaData();
  }

  @Override
  public void setURL(int parameterIndex, @Nullable URL x) throws SQLException {
    getStatement().setURL(parameterIndex, x);
  }

  @Override
  public ParameterMetaData getParameterMetaData() throws SQLException {
    return getStatement().getParameterMetaData();
  }

  @Override
  public void setRowId(int parameterIndex, RowId x) throws SQLException {
    getStatement().setRowId(parameterIndex, x);
  }

  @Override
  public void setNString(int parameterIndex, @Nullable String value) throws SQLException {
    getStatement().setNString(parameterIndex, value);
  }

  @Override
  public void setNClob(int parameterIndex, @Nullable NClob value) throws SQLException {
    getStatement().setNClob(parameterIndex, value);
  }

  @Override
  public void setNClob(int parameterIndex, @Nullable Reader reader) throws SQLException {
    getStatement().setNClob(parameterIndex, reader);
  }

  @Override
  public void setNClob(int parameterIndex, @Nullable Reader reader, long length)
      throws SQLException {
    getStatement().setNClob(parameterIndex, reader, length);
  }

  @Override
  public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
    getStatement().setSQLXML(parameterIndex, xmlObject);
  }

  @Override
  public void setObject(int parameterIndex, @Nullable Object x, int targetSqlType,
      int scaleOrLength)
      throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
  }

  @Override
  public void setObject(int parameterIndex, @Nullable Object x, int targetSqlType)
      throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType);
  }

  @Override
  public void setObject(int parameterIndex, @Nullable Object x) throws SQLException {
    getStatement().setObject(parameterIndex, x);
  }

  @Override
  public void setObject(int parameterIndex, @Nullable Object x, SQLType targetSqlType,
      int scaleOrLength)
      throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
  }

  @Override
  public void setObject(int parameterIndex, @Nullable Object x, SQLType targetSqlType)
      throws SQLException {
    getStatement().setObject(parameterIndex, x, targetSqlType);
  }

  @Override
  public void setAsciiStream(int parameterIndex, @Nullable InputStream x) throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x);
  }

  @Override
  public void setAsciiStream(int parameterIndex, @Nullable InputStream x, int length)
      throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x, length);
  }

  @Override
  public void setAsciiStream(int parameterIndex, @Nullable InputStream x, long length)
      throws SQLException {
    getStatement().setAsciiStream(parameterIndex, x, length);
  }

  @Override
  public void setBinaryStream(int parameterIndex, @Nullable InputStream x) throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x);
  }

  @Override
  public void setBinaryStream(int parameterIndex, @Nullable InputStream x, int length)
      throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x, length);
  }

  @Override
  public void setBinaryStream(int parameterIndex, @Nullable InputStream x, long length)
      throws SQLException {
    getStatement().setBinaryStream(parameterIndex, x, length);
  }

  @Override
  public void setCharacterStream(int parameterIndex, @Nullable Reader reader) throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader);
  }

  @Override
  public void setCharacterStream(int parameterIndex, @Nullable Reader reader, int length)
      throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader, length);
  }

  @Override
  public void setCharacterStream(int parameterIndex, @Nullable Reader reader, long length)
      throws SQLException {
    getStatement().setCharacterStream(parameterIndex, reader, length);
  }

  @Override
  public void setNCharacterStream(int parameterIndex, @Nullable Reader value) throws SQLException {
    getStatement().setNCharacterStream(parameterIndex, value);
  }

  @Override
  public void setNCharacterStream(int parameterIndex, @Nullable Reader value, long length)
      throws SQLException {
    getStatement().setNCharacterStream(parameterIndex, value, length);
  }

  @Override
  public long executeLargeUpdate() throws SQLException {
    return getStatement().executeLargeUpdate();
  }

  private static SqlException getSetException(int parameterIndex, Class<?> clazz,
      @Nullable Object value, SQLException e) {
    return new SqlException("Sql exception setting bind on index " + parameterIndex
        + ", type " + clazz.getSimpleName() + " to value " + value, e);
  }

  /**
   * Converts unchecked SQLException in setNull to runtime exception.
   *
   * @param parameterIndex is index of parameter to be set to null
   * @param type           is java class representing the value
   * @param sqlType        is sql type corresponding to bind variable
   */
  private void setNullInternal(int parameterIndex, Class<?> type, int sqlType) {
    try {
      setNull(parameterIndex, sqlType);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, type, null, e);
    }
  }

  @Override
  public void setNonNullDbBoolean(int parameterIndex, boolean value) {
    try {
      setBoolean(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, DtBoolean.class, value, e);
    }
  }

  @Override
  public void setNullableDbBoolean(int parameterIndex, @Nullable Boolean value) {
    if (value == null) {
      setNullInternal(parameterIndex, DtBoolean.class, Types.BOOLEAN);
    } else {
      setNonNullDbBoolean(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullBoolean(int parameterIndex, boolean value) {
    try {
      setString(parameterIndex, DtBoolean.toProvysDb(value));
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Boolean.class, value, e);
    }
  }

  @Override
  public void setNullableBoolean(int parameterIndex, @Nullable Boolean value) {
    if (value == null) {
      setNullInternal(parameterIndex, Boolean.class, Types.CHAR);
    } else {
      setNonNullBoolean(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullByte(int parameterIndex, byte value) {
    try {
      setByte(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Byte.class, value, e);
    }
  }

  @Override
  public void setNullableByte(int parameterIndex, @Nullable Byte value) {
    if (value == null) {
      setNullInternal(parameterIndex, Byte.class, Types.TINYINT);
    } else {
      setNonNullByte(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullShort(int parameterIndex, short value) {
    try {
      setShort(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Short.class, value, e);
    }
  }

  @Override
  public void setNullableShort(int parameterIndex, @Nullable Short value) {
    if (value == null) {
      setNullInternal(parameterIndex, Short.class, Types.SMALLINT);
    } else {
      setNonNullShort(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullInteger(int parameterIndex, int value) {
    try {
      setInt(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Integer.class, value, e);
    }
  }

  @Override
  public void setNullableInteger(int parameterIndex, @Nullable Integer value) {
    if (value == null) {
      setNullInternal(parameterIndex, Integer.class, Types.INTEGER);
    } else {
      setNonNullInteger(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullFloat(int parameterIndex, float value) {
    try {
      setFloat(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Float.class, value, e);
    }
  }

  @Override
  public void setNullableFloat(int parameterIndex, @Nullable Float value) {
    if (value == null) {
      setNullInternal(parameterIndex, Float.class, Types.FLOAT);
    } else {
      setNonNullFloat(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullDouble(int parameterIndex, double value) {
    try {
      setDouble(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Double.class, value, e);
    }
  }

  @Override
  public void setNullableDouble(int parameterIndex, @Nullable Double value) {
    if (value == null) {
      setNullInternal(parameterIndex, Double.class, Types.DOUBLE);
    } else {
      setNonNullDouble(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullCharacter(int parameterIndex, char value) {
    try {
      setString(parameterIndex, String.valueOf(value));
    } catch (SQLException e) {
      throw getSetException(parameterIndex, Character.class, value, e);
    }
  }

  @Override
  public void setNullableCharacter(int parameterIndex, @Nullable Character value) {
    if (value == null) {
      setNullInternal(parameterIndex, Character.class, Types.CHAR);
    } else {
      setNonNullCharacter(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullString(int parameterIndex, String value) {
    try {
      setString(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, String.class, value, e);
    }
  }

  @Override
  public void setNullableString(int parameterIndex, @Nullable String value) {
    if (value == null) {
      setNullInternal(parameterIndex, String.class, Types.VARCHAR);
    } else {
      setNonNullString(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullBigDecimal(int parameterIndex, BigDecimal value) {
    try {
      setBigDecimal(parameterIndex, value);
    } catch (SQLException e) {
      throw getSetException(parameterIndex, BigDecimal.class, value, e);
    }
  }

  @Override
  public void setNullableBigDecimal(int parameterIndex, @Nullable BigDecimal value) {
    if (value == null) {
      setNullInternal(parameterIndex, BigDecimal.class, Types.NUMERIC);
    } else {
      setNonNullBigDecimal(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullBigInteger(int parameterIndex, BigInteger value) {
    try {
      setBigDecimal(parameterIndex, new BigDecimal(value, 0));
    } catch (SQLException e) {
      throw getSetException(parameterIndex, BigInteger.class, value, e);
    }
  }

  @Override
  public void setNullableBigInteger(int parameterIndex, @Nullable BigInteger value) {
    if (value == null) {
      setNullInternal(parameterIndex, BigInteger.class, Types.BIGINT);
    } else {
      setNonNullBigInteger(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullDtUid(int parameterIndex, DtUid value) {
    try {
      setBigDecimal(parameterIndex, new BigDecimal(value.getValue(), 0));
    } catch (SQLException e) {
      throw getSetException(parameterIndex, DtUid.class, value, e);
    }
  }

  @Override
  public void setNullableDtUid(int parameterIndex, @Nullable DtUid value) {
    if (value == null) {
      setNullInternal(parameterIndex, DtUid.class, Types.NUMERIC);
    } else {
      setNonNullDtUid(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullDtDate(int parameterIndex, DtDate value) {
    try {
      setDate(parameterIndex, Date.valueOf(value.getLocalDate()));
    } catch (SQLException e) {
      throw getSetException(parameterIndex, DtDate.class, value, e);
    }
  }

  @Override
  public void setNullableDtDate(int parameterIndex, @Nullable DtDate value) {
    if (value == null) {
      setNullInternal(parameterIndex, DtDate.class, Types.DATE);
    } else {
      setNonNullDtDate(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullDtDateTime(int parameterIndex, DtDateTime value) {
    try {
      setTimestamp(parameterIndex, Timestamp.valueOf(value.getLocalDateTime()));
    } catch (SQLException e) {
      throw getSetException(parameterIndex, DtDateTime.class, value, e);
    }
  }

  @Override
  public void setNullableDtDateTime(int parameterIndex, @Nullable DtDateTime value) {
    if (value == null) {
      setNullInternal(parameterIndex, DtDateTime.class, Types.DATE);
    } else {
      setNonNullDtDateTime(parameterIndex, value);
    }
  }

  @Override
  public void setNonNullValue(int parameterIndex, Object value) {
    getSqlTypeMap().bindValue(this, parameterIndex, value);
  }

  @Override
  public <V> void setNonNullValue(int parameterIndex, V value, Class<V> type) {
    setNullableValue(parameterIndex, value, type);
  }

  @Override
  public <V> void setNullableValue(int parameterIndex, @Nullable V value, Class<V> type) {
    getSqlTypeMap().bindValue(this, parameterIndex, value, type);
  }

  @Override
  public String toString() {
    return "DefaultPreparedStatement{"
        + "sql='" + sql + '\''
        + ", " + super.toString() + '}';
  }
}
