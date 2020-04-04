package com.provys.db.defaultdb.dbcontext;

import com.provys.db.dbcontext.DbCallableStatement;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbStatement;
import com.provys.db.dbcontext.SqlException;
import com.provys.db.dbcontext.SqlTypeHandler;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.ShardingKey;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Provys database connection wrapper. Delegates functionality to {@code Connection}, but returns
 * Provys specific wrappers on retrieved Statements, that enable manipulation with Provys specific
 * framework data types and their mapping to database types and support logging and monitoring.
 */
public class DefaultConnection implements DbConnection {

  private final Connection connection;
  private final SqlTypeHandler sqlTypeHandler;

  /**
   * Create Provys connection as wrapper around Jdbc connection.
   *
   * @param connection is wrapped connection
   * @param sqlTypeHandler is type map used for this connection
   */
  public DefaultConnection(Connection connection, SqlTypeHandler sqlTypeHandler) {
    this.connection = connection;
    this.sqlTypeHandler = sqlTypeHandler;
  }

  @Override
  public String nativeSQL(String sql) throws SQLException {
    return connection.nativeSQL(sql);
  }

  @Override
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    connection.setAutoCommit(autoCommit);
  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    return connection.getAutoCommit();
  }

  @Override
  public void commit() throws SQLException {
    connection.commit();
  }

  @Override
  public void rollback() throws SQLException {
    connection.rollback();
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {
    connection.rollback(savepoint);
  }

  @Override
  public void close() throws SQLException {
    connection.close();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return connection.isClosed();
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return connection.getMetaData();
  }

  @Override
  public void setReadOnly(boolean readOnly) throws SQLException {
    connection.setReadOnly(readOnly);
  }

  @Override
  public boolean isReadOnly() throws SQLException {
    return connection.isReadOnly();
  }

  @Override
  public void setCatalog(String catalog) throws SQLException {
    connection.setCatalog(catalog);
  }

  @Override
  public String getCatalog() throws SQLException {
    return connection.getCatalog();
  }

  @Override
  public void setTransactionIsolation(int level) throws SQLException {
    connection.setTransactionIsolation(level);
  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    return connection.getTransactionIsolation();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return connection.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    connection.clearWarnings();
  }

  @Override
  public DbStatement createStatement() {
    try {
      return new DefaultStatement<>(connection.createStatement(), sqlTypeHandler);
    } catch (SQLException e) {
      throw new SqlException("Failed to create statement", e);
    }
  }

  @Override
  public DbStatement createStatement(int resultSetType, int resultSetConcurrency) {
    try {
      return new DefaultStatement<>(connection.createStatement(resultSetType, resultSetConcurrency),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw new SqlException("Failed to create statement", e);
    }
  }

  @Override
  public DbStatement createStatement(int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) {
    try {
      return new DefaultStatement<>(
          connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw new SqlException("Failed to create statement", e);
    }
  }

  private static SqlException getParsePreparedException(String sql, SQLException e) {
    return new SqlException("Failed to parse prepared statement:\n" + sql, e);
  }

  @Override
  public DbPreparedStatement prepareStatement(String sql) {
    try {
      return new DefaultPreparedStatement<>(sql, connection.prepareStatement(sql), sqlTypeHandler);
    } catch (SQLException e) {
      throw getParsePreparedException(sql, e);
    }
  }

  @Override
  public DbPreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency) {
    try {
      return new DefaultPreparedStatement<>(sql,
          connection.prepareStatement(sql, resultSetType, resultSetConcurrency), sqlTypeHandler);
    } catch (SQLException e) {
      throw getParsePreparedException(sql, e);
    }
  }

  @Override
  public DbPreparedStatement prepareStatement(String sql, int resultSetType,
      int resultSetConcurrency, int resultSetHoldability) {
    try {
      return new DefaultPreparedStatement<>(sql, connection
          .prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw getParsePreparedException(sql, e);
    }
  }

  @Override
  public DbPreparedStatement prepareStatement(String sql, int autoGeneratedKeys) {
    try {
      return new DefaultPreparedStatement<>(sql,
          connection.prepareStatement(sql, autoGeneratedKeys), sqlTypeHandler);
    } catch (SQLException e) {
      throw getParsePreparedException(sql, e);
    }
  }

  @Override
  public DbPreparedStatement prepareStatement(String sql, int[] columnIndexes) {
    try {
      return new DefaultPreparedStatement<>(sql, connection.prepareStatement(sql, columnIndexes),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw getParsePreparedException(sql, e);
    }
  }

  @Override
  public DbPreparedStatement prepareStatement(String sql, String[] columnNames) {
    try {
      return new DefaultPreparedStatement<>(sql, connection.prepareStatement(sql, columnNames),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw getParsePreparedException(sql, e);
    }
  }

  private static SqlException getParseCallableException(String sql, SQLException e) {
    return new SqlException("Failed to parse callable statement:\n" + sql, e);
  }

  @Override
  public DbCallableStatement prepareCall(String sql) {
    try {
      return new DefaultCallableStatement<>(sql, connection.prepareCall(sql), sqlTypeHandler);
    } catch (SQLException e) {
      throw getParseCallableException(sql, e);
    }
  }

  @Override
  public DbCallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) {
    try {
      return new DefaultCallableStatement<>(sql,
          connection.prepareCall(sql, resultSetType, resultSetConcurrency),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw getParseCallableException(sql, e);
    }
  }

  @Override
  public DbCallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
      int resultSetHoldability) {
    try {
      return new DefaultCallableStatement<>(sql,
          connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
          sqlTypeHandler);
    } catch (SQLException e) {
      throw getParseCallableException(sql, e);
    }
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return connection.getTypeMap();
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    connection.setTypeMap(map);
  }

  @Override
  public void setHoldability(int holdability) throws SQLException {
    connection.setHoldability(holdability);
  }

  @Override
  public int getHoldability() throws SQLException {
    return connection.getHoldability();
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    return connection.setSavepoint();
  }

  @Override
  public Savepoint setSavepoint(String name) throws SQLException {
    return connection.setSavepoint(name);
  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    connection.releaseSavepoint(savepoint);
  }

  @Override
  public Clob createClob() throws SQLException {
    return connection.createClob();
  }

  @Override
  public Blob createBlob() throws SQLException {
    return connection.createBlob();
  }

  @Override
  public NClob createNClob() throws SQLException {
    return connection.createNClob();
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return connection.createSQLXML();
  }

  @Override
  public boolean isValid(int timeout) throws SQLException {
    return connection.isValid(timeout);
  }

  @Override
  public void setClientInfo(String name, String value) throws SQLClientInfoException {
    connection.setClientInfo(name, value);
  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {
    connection.setClientInfo(properties);
  }

  @Override
  public String getClientInfo(String name) throws SQLException {
    return connection.getClientInfo(name);
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return connection.getClientInfo();
  }

  @Override
  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return connection.createArrayOf(typeName, elements);
  }

  @Override
  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return connection.createStruct(typeName, attributes);
  }

  @Override
  public void setSchema(String schema) throws SQLException {
    connection.setSchema(schema);
  }

  @Override
  public String getSchema() throws SQLException {
    return connection.getSchema();
  }

  @Override
  public void abort(Executor executor) throws SQLException {
    connection.abort(executor);
  }

  @Override
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    connection.setNetworkTimeout(executor, milliseconds);
  }

  @Override
  public int getNetworkTimeout() throws SQLException {
    return connection.getNetworkTimeout();
  }

  @Override
  public void beginRequest() throws SQLException {
    connection.beginRequest();
  }

  @Override
  public void endRequest() throws SQLException {
    connection.endRequest();
  }

  @Override
  public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey superShardingKey,
      int timeout) throws SQLException {
    return connection.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
  }

  @Override
  public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout) throws SQLException {
    return connection.setShardingKeyIfValid(shardingKey, timeout);
  }

  @Override
  public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey)
      throws SQLException {
    connection.setShardingKey(shardingKey, superShardingKey);
  }

  @Override
  public void setShardingKey(ShardingKey shardingKey) throws SQLException {
    connection.setShardingKey(shardingKey);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (iface.isInstance(this)) {
      return iface.cast(this);
    }
    return connection.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    if (iface.isInstance(this)) {
      return true;
    }
    return connection.isWrapperFor(iface);
  }

  @Override
  public String toString() {
    return "DefaultConnection{"
        + "connection=" + connection
        + ", sqlTypeHandler=" + sqlTypeHandler
        + '}';
  }
}
