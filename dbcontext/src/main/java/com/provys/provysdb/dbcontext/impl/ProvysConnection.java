package com.provys.provysdb.dbcontext.impl;

import com.provys.provysdb.dbcontext.DbCallableStatement;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbStatement;
import com.provys.provysdb.dbcontext.SqlTypeMap;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Provys database connection wrapper. Delegates functionality to {@code Connection}, but returns Provys specific
 * wrappers on retrieved Statements, that enable manipulation with Provys specific framework data types and their
 * mapping to database types and support logging and monitoring.
 */
class ProvysConnection implements DbConnection {

    @Nonnull
    private final Connection connection;
    @Nonnull
    private final SqlTypeMap sqlTypeMap;

    ProvysConnection(Connection connection, SqlTypeMap sqlTypeMap) {
        this.connection = connection;
        this.sqlTypeMap = sqlTypeMap;
    }

    @Override
    @Nonnull
    public DbStatement createStatement() throws SQLException {
        return new ProvysStatement<>(connection.createStatement());
    }

    @Override
    @Nonnull
    public DbPreparedStatement prepareStatement(String sql) throws SQLException {
        return new ProvysPreparedStatement<>(connection.prepareStatement(sql), sqlTypeMap);
    }

    @Override
    @Nonnull
    public DbCallableStatement prepareCall(String sql) throws SQLException {
        return new ProvysCallableStatement<>(connection.prepareCall(sql), sqlTypeMap);
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
    @Nonnull
    public DbStatement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ProvysStatement<>(connection.createStatement(resultSetType, resultSetConcurrency));
    }

    @Override
    @Nonnull
    public DbPreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ProvysPreparedStatement<>(connection.prepareStatement(sql, resultSetType, resultSetConcurrency),
                sqlTypeMap);
    }

    @Override
    @Nonnull
    public DbCallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ProvysCallableStatement<>(connection.prepareCall(sql, resultSetType, resultSetConcurrency),
                sqlTypeMap);
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
    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        connection.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    @Nonnull
    public DbCallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new ProvysCallableStatement<>(connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sqlTypeMap);
    }

    @Override
    @Nonnull
    public DbPreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return new ProvysPreparedStatement<>(connection.prepareStatement(sql, autoGeneratedKeys), sqlTypeMap);
    }

    @Override
    @Nonnull
    public DbPreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return new ProvysPreparedStatement<>(connection.prepareStatement(sql, columnIndexes), sqlTypeMap);
    }

    @Override
    @Nonnull
    public DbPreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return new ProvysPreparedStatement<>(connection.prepareStatement(sql, columnNames), sqlTypeMap);
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
    public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey superShardingKey, int timeout) throws SQLException {
        return connection.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
    }

    @Override
    public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout) throws SQLException {
        return connection.setShardingKeyIfValid(shardingKey, timeout);
    }

    @Override
    public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey) throws SQLException {
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
}
