package com.provys.provysdb.dbcontext.impl;

import com.provys.common.datatype.DtBoolean;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.dbcontext.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

@SuppressWarnings("unused")
class ProvysPreparedStatement<T extends PreparedStatement> extends ProvysStatement<T>
        implements DbPreparedStatement {

    @Nonnull
    private final SqlTypeMap sqlTypeMap;

    ProvysPreparedStatement(T preparedStatement, SqlTypeMap sqlTypeMap) {
        super(preparedStatement);
        this.sqlTypeMap = sqlTypeMap;
    }

    /**
     * @return value of field sqlTypeMap
     */
    @Nonnull
    SqlTypeMap getSqlTypeMap() {
        return sqlTypeMap;
    }

    @Override
    public DbResultSet executeQuery() throws SQLException {
        return new ProvysResultSet(statement.executeQuery());
    }

    @Override
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        statement.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        statement.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        statement.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        statement.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        statement.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        statement.setLong(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        statement.setFloat(parameterIndex, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        statement.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        statement.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        statement.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        statement.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        statement.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        statement.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        statement.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    @Deprecated(since = "1.2")
    @SuppressWarnings({"squid:MissingDeprecatedCheck", "squid:S1133"})
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        statement.clearParameters();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        statement.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        return statement.execute();
    }

    @Override
    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        statement.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        statement.setBlob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        statement.setClob(parameterIndex, x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        statement.setArray(parameterIndex, x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return statement.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        statement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        statement.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        statement.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        statement.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        statement.setURL(parameterIndex, x);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return statement.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        statement.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        statement.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        statement.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        statement.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        statement.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        statement.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        statement.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        statement.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        statement.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        statement.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        statement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        statement.setNClob(parameterIndex, reader);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        return statement.executeLargeUpdate();
    }

    @Nonnull
    private SqlException getSetException(int parameterIndex, Class<?> clazz, @Nullable Object value, SQLException e) {
        return new SqlException("Sql exception setting bind on index " + parameterIndex +
                ", type " + clazz.getSimpleName() + " to value " + value, e);
    }

    @Override
    public void setNonnullDbBoolean(int parameterIndex, boolean value) {
        try {
            setBoolean(parameterIndex, value);
        } catch (SQLException e) {
            throw getSetException(parameterIndex, DtBoolean.class, value, e);
        }
    }

    @Override
    public void setNullableDbBoolean(int parameterIndex, @Nullable Boolean value) {
        try {
            if (value == null) {
                setNull(parameterIndex, Types.BOOLEAN);
            } else {
                setNonnullDbBoolean(parameterIndex, value);
            }
        } catch (SQLException e) {
            throw getSetException(parameterIndex, DtBoolean.class, value, e);
        }
    }

    @Override
    public void setNonnullBoolean(int parameterIndex, boolean value) {
        try {
            setString(parameterIndex, DtBoolean.toProvysDb(value));
        } catch (SQLException e) {
            throw getSetException(parameterIndex, Boolean.class, value, e);
        }
    }

    @Override
    public void setNullableBoolean(int parameterIndex, @Nullable Boolean value) {
        try {
            if (value == null) {
                setNull(parameterIndex, Types.CHAR);
            } else {
                setNonnullBoolean(parameterIndex, value);
            }
        } catch (SQLException e) {
            throw getSetException(parameterIndex, Boolean.class, value, e);
        }
    }

    @Override
    public void setNonnullDtUid(int parameterIndex, DtUid value) {
        try {
            setBigDecimal(parameterIndex, new BigDecimal(value.getValue(), 0));
        } catch (SQLException e) {
            throw getSetException(parameterIndex, DtUid.class, value, e);
        }
    }

    @Override
    public void setNullableDtUid(int parameterIndex, @Nullable DtUid value) {
        try {
            if (value == null) {
                setNull(parameterIndex, Types.NUMERIC);
            } else {
                setBigDecimal(parameterIndex, new BigDecimal(value.getValue(), 0));
            }
        } catch (SQLException e) {
            throw getSetException(parameterIndex, DtUid.class, value, e);
        }
    }

    @Override
    public void setNonnullValue(int parameterIndex, Object value) {
        //noinspection unchecked - we know that we get adapter for proper type... there is just no way to express it
        ((SqlTypeAdapter<Object>) sqlTypeMap.getAdapter(value.getClass())).bindValue(this, parameterIndex, value);
    }

    @Override
    public <V> void setNullableValue(int parameterIndex, @Nullable V value, Class<V> type) {
        sqlTypeMap.getAdapter(type).bindValue(this, parameterIndex, value);
    }

    @Override
    public SqlTypeMap getAdapterMap() {
        return sqlTypeMap;
    }
}
