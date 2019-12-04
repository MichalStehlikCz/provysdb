package com.provys.provysdb.dbcontext.impl;

import com.provys.common.datatype.DtBoolean;
import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.SqlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

/**
 * Wrapper on {@code ResultSet}, adding support for Provys framework specific data types
 */
@SuppressWarnings("unused")
class ProvysResultSetImpl implements DbResultSet {

    private static final Logger LOG = LogManager.getLogger(ProvysResultSetImpl.class);

    private final ResultSet resultSet;

    ProvysResultSetImpl(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public boolean next() throws SQLException {
        return resultSet.next();
    }

    @Override
    public void close() throws SQLException {
        resultSet.close();
    }

    @Override
    public boolean wasNull() throws SQLException {
        return resultSet.wasNull();
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return resultSet.getBoolean(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return resultSet.getByte(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return resultSet.getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return resultSet.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return resultSet.getLong(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return resultSet.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return resultSet.getDouble(columnIndex);
    }

    @Override
    @Deprecated(since = "1.2")
    @SuppressWarnings({"squid:MissingDeprecatedCheck", "squid:S1133"})
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return resultSet.getBigDecimal(columnIndex, scale);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return resultSet.getBytes(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return resultSet.getDate(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return resultSet.getTime(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return resultSet.getTimestamp(columnIndex);
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return resultSet.getAsciiStream(columnIndex);
    }

    @Override
    @Deprecated(since = "1.2")
    @SuppressWarnings({"squid:MissingDeprecatedCheck", "squid:S1133"})
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return resultSet.getUnicodeStream(columnIndex);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return resultSet.getBinaryStream(columnIndex);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        return resultSet.getString(columnLabel);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return resultSet.getBoolean(columnLabel);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return resultSet.getByte(columnLabel);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        return resultSet.getShort(columnLabel);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return resultSet.getInt(columnLabel);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return resultSet.getLong(columnLabel);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return resultSet.getFloat(columnLabel);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return resultSet.getDouble(columnLabel);
    }

    @Override
    @Deprecated(since = "1.2")
    @SuppressWarnings({"squid:MissingDeprecatedCheck", "squid:S1133"})
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return resultSet.getBigDecimal(columnLabel, scale);
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return resultSet.getBytes(columnLabel);
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return resultSet.getDate(columnLabel);
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return resultSet.getTime(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return resultSet.getTimestamp(columnLabel);
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return resultSet.getAsciiStream(columnLabel);
    }

    @Override
    @Deprecated(since = "1.2")
    @SuppressWarnings({"squid:MissingDeprecatedCheck", "squid:S1133"})
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return resultSet.getUnicodeStream(columnLabel);
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return resultSet.getBinaryStream(columnLabel);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return resultSet.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        resultSet.clearWarnings();
    }

    @Override
    public String getCursorName() throws SQLException {
        return resultSet.getCursorName();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return resultSet.getMetaData();
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return resultSet.getObject(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return resultSet.getObject(columnLabel);
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return resultSet.findColumn(columnLabel);
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return resultSet.getCharacterStream(columnIndex);
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return resultSet.getCharacterStream(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return resultSet.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return resultSet.getBigDecimal(columnLabel);
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return resultSet.isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return resultSet.isAfterLast();
    }

    @Override
    public boolean isFirst() throws SQLException {
        return resultSet.isFirst();
    }

    @Override
    @SuppressWarnings("squid:S2232")
    public boolean isLast() throws SQLException {
        return resultSet.isLast();
    }

    @Override
    public void beforeFirst() throws SQLException {
        resultSet.beforeFirst();
    }

    @Override
    public void afterLast() throws SQLException {
        resultSet.afterLast();
    }

    @Override
    public boolean first() throws SQLException {
        return resultSet.first();
    }

    @Override
    public boolean last() throws SQLException {
        return resultSet.last();
    }

    @Override
    public int getRow() throws SQLException {
        return resultSet.getRow();
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        return resultSet.absolute(row);
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        return resultSet.relative(rows);
    }

    @Override
    public boolean previous() throws SQLException {
        return resultSet.previous();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        resultSet.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return resultSet.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        resultSet.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return resultSet.getFetchSize();
    }

    @Override
    public int getType() throws SQLException {
        return resultSet.getType();
    }

    @Override
    public int getConcurrency() throws SQLException {
        return resultSet.getConcurrency();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return resultSet.rowUpdated();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return resultSet.rowInserted();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return resultSet.rowDeleted();
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        resultSet.updateNull(columnIndex);
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        resultSet.updateBoolean(columnIndex, x);
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        resultSet.updateByte(columnIndex, x);
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        resultSet.updateShort(columnIndex, x);
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        resultSet.updateInt(columnIndex, x);
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        resultSet.updateLong(columnIndex, x);
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        resultSet.updateFloat(columnIndex, x);
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        resultSet.updateDouble(columnIndex, x);
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        resultSet.updateBigDecimal(columnIndex, x);
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        resultSet.updateString(columnIndex, x);
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        resultSet.updateBytes(columnIndex, x);
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        resultSet.updateDate(columnIndex, x);
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        resultSet.updateTime(columnIndex, x);
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        resultSet.updateTimestamp(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        resultSet.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        resultSet.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        resultSet.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        resultSet.updateObject(columnIndex, x, scaleOrLength);
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        resultSet.updateObject(columnIndex, x);
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        resultSet.updateNull(columnLabel);
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        resultSet.updateBoolean(columnLabel, x);
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        resultSet.updateByte(columnLabel, x);
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        resultSet.updateShort(columnLabel, x);
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        resultSet.updateInt(columnLabel, x);
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        resultSet.updateLong(columnLabel, x);
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        resultSet.updateFloat(columnLabel, x);
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        resultSet.updateDouble(columnLabel, x);
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        resultSet.updateBigDecimal(columnLabel, x);
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        resultSet.updateString(columnLabel, x);
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        resultSet.updateBytes(columnLabel, x);
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        resultSet.updateDate(columnLabel, x);
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        resultSet.updateTime(columnLabel, x);
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        resultSet.updateTimestamp(columnLabel, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        resultSet.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        resultSet.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        resultSet.updateObject(columnLabel, x, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        resultSet.updateObject(columnLabel, x);
    }

    @Override
    public void insertRow() throws SQLException {
        resultSet.insertRow();
    }

    @Override
    public void updateRow() throws SQLException {
        resultSet.updateRow();
    }

    @Override
    public void deleteRow() throws SQLException {
        resultSet.deleteRow();
    }

    @Override
    public void refreshRow() throws SQLException {
        resultSet.refreshRow();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        resultSet.cancelRowUpdates();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        resultSet.moveToInsertRow();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        resultSet.moveToCurrentRow();
    }

    @Override
    public Statement getStatement() throws SQLException {
        return resultSet.getStatement();
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return resultSet.getObject(columnIndex, map);
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        return resultSet.getRef(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return resultSet.getBlob(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return resultSet.getClob(columnIndex);
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        return resultSet.getArray(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return resultSet.getObject(columnLabel, map);
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        return resultSet.getRef(columnLabel);
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        return resultSet.getBlob(columnLabel);
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        return resultSet.getClob(columnLabel);
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        return resultSet.getArray(columnLabel);
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return resultSet.getDate(columnIndex, cal);
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return resultSet.getDate(columnLabel, cal);
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return resultSet.getTime(columnIndex, cal);
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return resultSet.getTime(columnLabel, cal);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return resultSet.getTimestamp(columnIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return resultSet.getTimestamp(columnLabel, cal);
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return resultSet.getURL(columnIndex);
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        return resultSet.getURL(columnLabel);
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        resultSet.updateRef(columnIndex, x);
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        resultSet.updateRef(columnLabel, x);
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        resultSet.updateBlob(columnIndex, x);
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        resultSet.updateBlob(columnLabel, x);
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        resultSet.updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        resultSet.updateClob(columnLabel, x);
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        resultSet.updateArray(columnIndex, x);
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        resultSet.updateArray(columnLabel, x);
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        return resultSet.getRowId(columnIndex);
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        return resultSet.getRowId(columnLabel);
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        resultSet.updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        resultSet.updateRowId(columnLabel, x);
    }

    @Override
    public int getHoldability() throws SQLException {
        return resultSet.getHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return resultSet.isClosed();
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        resultSet.updateNString(columnIndex, nString);
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        resultSet.updateNString(columnLabel, nString);
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        resultSet.updateNClob(columnIndex, nClob);
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        resultSet.updateNClob(columnLabel, nClob);
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        return resultSet.getNClob(columnIndex);
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        return resultSet.getNClob(columnLabel);
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return resultSet.getSQLXML(columnIndex);
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return resultSet.getSQLXML(columnLabel);
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        resultSet.updateSQLXML(columnIndex, xmlObject);
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        resultSet.updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return resultSet.getNString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return resultSet.getNString(columnLabel);
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return resultSet.getNCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return resultSet.getNCharacterStream(columnLabel);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        resultSet.updateNCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateNCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        resultSet.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        resultSet.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        resultSet.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        resultSet.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        resultSet.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        resultSet.updateBlob(columnIndex, inputStream, length);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        resultSet.updateBlob(columnLabel, inputStream, length);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        resultSet.updateClob(columnIndex, reader, length);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateClob(columnLabel, reader, length);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        resultSet.updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        resultSet.updateNClob(columnLabel, reader, length);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        resultSet.updateNCharacterStream(columnIndex, x);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        resultSet.updateAsciiStream(columnIndex, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        resultSet.updateBinaryStream(columnIndex, x);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        resultSet.updateCharacterStream(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        resultSet.updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        resultSet.updateBinaryStream(columnLabel, x);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        resultSet.updateBlob(columnIndex, inputStream);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        resultSet.updateBlob(columnLabel, inputStream);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        resultSet.updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        resultSet.updateNClob(columnIndex, reader);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        resultSet.updateNClob(columnLabel, reader);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return resultSet.getObject(columnIndex, type);
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return resultSet.getObject(columnLabel, type);
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        resultSet.updateObject(columnIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        resultSet.updateObject(columnLabel, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
        resultSet.updateObject(columnIndex, x, targetSqlType);
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
        resultSet.updateObject(columnLabel, x, targetSqlType);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return iface.cast(this);
        }
        return resultSet.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return true;
        }
        return resultSet.isWrapperFor(iface);
    }

    @Nonnull
    private SqlException getNullException(int columnIndex) {
        return new SqlException(LOG, "Unexpected null value in ResultSet, column index " + columnIndex);
    }

    @Nonnull
    private SqlException getNullException(String columnLabel) {
        return new SqlException(LOG, "Unexpected null value in ResultSet, column label " + columnLabel);
    }

    private void checkWasNotNull(int columnIndex) throws SQLException {
        if (resultSet.wasNull()) {
            throw getNullException(columnIndex);
        }
    }

    private void checkWasNotNull(String columnLabel) throws SQLException {
        if (resultSet.wasNull()) {
            throw getNullException(columnLabel);
        }
    }

    @Nonnull
    private SqlException getGetSqlException(int columnIndex, Class<?> type, SQLException e) {
        return new SqlException(LOG, "Error reading " + type.getSimpleName() + " value from ResultSet, column "
                + columnIndex, e);
    }

    @Nonnull
    private SqlException getGetSqlException(String columnLabel, Class<?> type, SQLException e) {
        return new SqlException(LOG, "Error reading " + type.getSimpleName() + " value from ResultSet, column "
                + columnLabel, e);
    }

    @Override
    public boolean getNonnullBoolean(int columnIndex) {
        var result = getNullableBoolean(columnIndex);
        if (result == null) {
            throw getNullException(columnIndex);
        }
        return result;
    }

    @Override
    public boolean getNonnullBoolean(String columnLabel) {
        var result = getNullableBoolean(columnLabel);
        if (result == null) {
            throw getNullException(columnLabel);
        }
        return result;
    }

    @Nullable
    @Override
    public Boolean getNullableBoolean(int columnIndex) {
        try {
            String dbValue = resultSet.getString(columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return DtBoolean.ofProvysDb(dbValue);
        } catch (InternalException e) {
            throw new SqlException(LOG, "Incorrect Provys boolean value retrieved from column index " + columnIndex, e);
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, Boolean.class, e);
        }
    }

    @Nullable
    @Override
    public Boolean getNullableBoolean(String columnLabel) {
        try {
            String dbValue = resultSet.getString(columnLabel);
            if (resultSet.wasNull()) {
                return null;
            }
            return DtBoolean.ofProvysDb(dbValue);
        } catch (InternalException e) {
            throw new SqlException(LOG, "Incorrect Provys boolean value retrieved from column " + columnLabel, e);
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, Boolean.class, e);
        }
    }

    @Override
    @Nonnull
    public Optional<Boolean> getOptionalBoolean(int columnIndex) {
        return Optional.ofNullable(getNullableBoolean(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<Boolean> getOptionalBoolean(String columnLabel) {
        return Optional.ofNullable(getNullableBoolean(columnLabel));
    }

    @Override
    public byte getNonnullByte(int columnIndex) {
        try {
            var value = resultSet.getByte(columnIndex);
            checkWasNotNull(columnIndex);
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, byte.class, e);
        }
    }

    @Override
    public byte getNonnullByte(String columnLabel) {
        try {
            var value = resultSet.getByte(columnLabel);
            checkWasNotNull(columnLabel);
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, byte.class, e);
        }
    }

    @Nullable
    @Override
    public Byte getNullableByte(int columnIndex) {
        try {
            var value = resultSet.getByte(columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, byte.class, e);
        }
    }

    @Nullable
    @Override
    public Byte getNullableByte(String columnLabel) {
        try {
            var value = resultSet.getByte(columnLabel);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, byte.class, e);
        }
    }

    @Override
    @Nonnull
    public Optional<Byte> getOptionalByte(int columnIndex) {
        return Optional.ofNullable(getNullableByte(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<Byte> getOptionalByte(String columnLabel) {
        return Optional.ofNullable(getNullableByte(columnLabel));
    }

    @Override
    public short getNonnullShort(int columnIndex) {
        try {
            var value = resultSet.getShort(columnIndex);
            checkWasNotNull(columnIndex);
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, short.class, e);
        }
    }

    @Override
    public short getNonnullShort(String columnLabel) {
        try {
            var value = resultSet.getShort(columnLabel);
            checkWasNotNull(columnLabel);
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, short.class, e);
        }
    }

    @Nullable
    @Override
    public Short getNullableShort(int columnIndex) {
        try {
            var value = resultSet.getShort(columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, short.class, e);
        }
    }

    @Nullable
    @Override
    public Short getNullableShort(String columnLabel) {
        try {
            var value = resultSet.getShort(columnLabel);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, short.class, e);
        }
    }

    @Override
    @Nonnull
    public Optional<Short> getOptionalShort(int columnIndex) {
        return Optional.ofNullable(getNullableShort(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<Short> getOptionalShort(String columnLabel) {
        return Optional.ofNullable(getNullableShort(columnLabel));
    }

    @Override
    public int getNonnullInteger(int columnIndex) {
        try {
            var value = resultSet.getInt(columnIndex);
            checkWasNotNull(columnIndex);
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, int.class, e);
        }
    }

    @Override
    public int getNonnullInteger(String columnLabel) {
        try {
            var value = resultSet.getInt(columnLabel);
            checkWasNotNull(columnLabel);
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, int.class, e);
        }
    }

    @Nullable
    @Override
    public Integer getNullableInteger(int columnIndex) {
        try {
            var value = resultSet.getInt(columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, int.class, e);
        }
    }

    @Nullable
    @Override
    public Integer getNullableInteger(String columnLabel) {
        try {
            var value = resultSet.getInt(columnLabel);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, int.class, e);
        }
    }

    @Override
    @Nonnull
    public Optional<Integer> getOptionalInteger(int columnIndex) {
        return Optional.ofNullable(getNullableInteger(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<Integer> getOptionalInteger(String columnLabel) {
        return Optional.ofNullable(getNullableInteger(columnLabel));
    }

    @Nonnull
    @Override
    public String getNonnullString(int columnIndex) {
        var result = getNullableString(columnIndex);
        if (result == null) {
            throw getNullException(columnIndex);
        }
        return result;
    }

    @Nonnull
    @Override
    public String getNonnullString(String columnLabel) {
        var result = getNullableString(columnLabel);
        if (result == null) {
            throw getNullException(columnLabel);
        }
        return result;
    }

    @Nullable
    @Override
    public String getNullableString(int columnIndex) {
        try {
            var value = resultSet.getString(columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, String.class, e);
        }
    }

    @Nullable
    @Override
    public String getNullableString(String columnLabel) {
        try {
            var value = resultSet.getString(columnLabel);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, String.class, e);
        }
    }

    @Override
    @Nonnull
    public Optional<String> getOptionalString(int columnIndex) {
        return Optional.ofNullable(getNullableString(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<String> getOptionalString(String columnLabel) {
        return Optional.ofNullable(getNullableString(columnLabel));
    }

    @Nonnull
    @Override
    public BigDecimal getNonnullBigDecimal(int columnIndex) {
        var result = getNullableBigDecimal(columnIndex);
        if (result == null) {
            throw getNullException(columnIndex);
        }
        return result;
    }

    @Nonnull
    @Override
    public BigDecimal getNonnullBigDecimal(String columnLabel) {
        var result = getNullableBigDecimal(columnLabel);
        if (result == null) {
            throw getNullException(columnLabel);
        }
        return result;
    }

    @Nullable
    @Override
    public BigDecimal getNullableBigDecimal(int columnIndex) {
        try {
            var value = resultSet.getBigDecimal(columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnIndex, BigDecimal.class, e);
        }
    }

    @Nullable
    @Override
    public BigDecimal getNullableBigDecimal(String columnLabel) {
        try {
            var value = resultSet.getBigDecimal(columnLabel);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw getGetSqlException(columnLabel, BigDecimal.class, e);
        }
    }

    @Override
    @Nonnull
    public Optional<BigDecimal> getOptionalBigDecimal(int columnIndex) {
        return Optional.ofNullable(getNullableBigDecimal(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<BigDecimal> getOptionalBigDecimal(String columnLabel) {
        return Optional.ofNullable(getNullableBigDecimal(columnLabel));
    }

    @Nonnull
    @Override
    public BigInteger getNonnullDtUid(int columnIndex) {
        var result = getNullableDtUid(columnIndex);
        if (result == null) {
            throw getNullException(columnIndex);
        }
        return result;
    }

    @Nonnull
    @Override
    public BigInteger getNonnullDtUid(String columnLabel) {
        var result = getNullableDtUid(columnLabel);
        if (result == null) {
            throw getNullException(columnLabel);
        }
        return result;
    }

    @Nullable
    @Override
    public BigInteger getNullableDtUid(int columnIndex) {
        var result = getNullableBigDecimal(columnIndex);
        try {
            return (result == null) ? null : result.toBigIntegerExact();
        } catch (ArithmeticException e) {
            throw new SqlException(LOG, "Fractional part encountered when reading Uid, column " + columnIndex);
        }
    }

    @Nullable
    @Override
    public BigInteger getNullableDtUid(String columnLabel) {
        var result = getNullableBigDecimal(columnLabel);
        try {
            return (result == null) ? null : result.toBigIntegerExact();
        } catch (ArithmeticException e) {
            throw new SqlException(LOG, "Fractional part encountered when reading Uid, column " + columnLabel);
        }
    }

    @Override
    @Nonnull
    public Optional<BigInteger> getOptionalDtUid(int columnIndex) {
        return Optional.ofNullable(getNullableDtUid(columnIndex));
    }

    @Override
    @Nonnull
    public Optional<BigInteger> getOptionalDtUid(String columnLabel) {
        return Optional.ofNullable(getNullableDtUid(columnLabel));
    }
}
