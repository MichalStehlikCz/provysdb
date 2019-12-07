package com.provys.provysdb.dbcontext.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import com.provys.provysdb.dbcontext.SqlException;
import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.admin.UniversalConnectionPoolManager;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.sql.CommonDataSource;
import javax.sql.DataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * {@code DataSource} to be used for accessing PROVYS database. Based on Oracle Universal Connection Pool,
 * this data-source supports switching of session context to one representing user session on
 * retrieval
 *
 * HikariCP was considered, but not chosen for now as it does not support connection
 * labeling; if there are any problems with Oracle UCP, we might switch to HikariCP or
 * other connection pool. Nothing outside this class should depend on used connection pool
 *
 * @author stehlik
 */
@SuppressWarnings("WeakerAccess")
class ProvysConnectionPoolDataSource implements DataSource, CommonDataSource {
    @Nonnull
    private static final Logger LOG = LogManager.getLogger(ProvysConnectionPoolDataSource.class);
    @Nonnull
    private static final String POOL_NAME = "ProvysDB";

    @Nonnull
    private final PoolDataSource oraclePool;

    /**
     * Constructor for provys connection that reads all info from environment.
     * Creates supporting Oracle Universal Connection Pool based on read connection information.
     */
    ProvysConnectionPoolDataSource() {
        UniversalConnectionPoolManager mgr;
        try {
            mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
            for (String name : mgr.getConnectionPoolNames()) {
                if (name.equals(POOL_NAME)) {
                    LOG.warn("Connection pool with name {} found; destroying it", POOL_NAME);
                    mgr.destroyConnectionPool(POOL_NAME);
                }
            }
        } catch (UniversalConnectionPoolException e) {
            LOG.warn("Exception checking connection pool existence", e);
        }
        String user = null;
        String db = null;
        try {
            oraclePool = PoolDataSourceFactory.getPoolDataSource();
            oraclePool.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            Config config = ConfigProvider.getConfig();
            user = config
                    .getOptionalValue("PROVYSDB_USER", String.class)
                    .orElse("KER");
            oraclePool.setUser(user);
            String pwd = config
                    .getOptionalValue("PROVYSDB_PWD", String.class)
                    .orElse("ker");
            oraclePool.setPassword(pwd);
            db = config
                    .getOptionalValue("PROVYSDB_URL", String.class)
                    .orElse("localhost:1521:PVYS");
            oraclePool.setURL("jdbc:oracle:thin:@" + db);
            oraclePool.setConnectionPoolName(POOL_NAME);
            int minPoolSize = config
                    .getOptionalValue("PROVYSDB_MINPOOLSIZE", Integer.class)
                    .orElse(1);
            oraclePool.setMinPoolSize(minPoolSize);
            oraclePool.setInitialPoolSize(minPoolSize);
            int maxPoolSize = config
                    .getOptionalValue("PROVYSDB_MAXPOOLSIZE", Integer.class)
                    .orElse(10);
            oraclePool.setMaxPoolSize(maxPoolSize);
            oraclePool.setValidateConnectionOnBorrow(true);
            // Register connection labeling callback
            oraclePool.registerConnectionLabelingCallback(new ProvysConnectionLabelingCallback());
            LOG.info("Connection pool created (user {}, db {}, minsize {}, maxsize {}", user, db, minPoolSize,
                    maxPoolSize);
        } catch (SQLException e) {
            throw new SqlException(LOG, "Failed to create connection pool (user " + user + ", db " + db + ")");
        }
        // now try to get connection (to verify that connection pool parameters are valid)
        try (Connection conn = getConnection()) {
            LOG.info("Verified connection to database (user {}, db {})", user, db);
        } catch (SQLException e) {
            LOG.warn("Failed to verify connection pool (user {}, db {}) - attempt to get connection thrown {}",
                    user, db, e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return oraclePool.getConnection();
    }

    public Connection getConnectionWithToken(String dbToken) throws SQLException {
        var reqLabels = new Properties();
        reqLabels.setProperty(ProvysConnectionLabelingCallback.PROPERTY_TOKEN, dbToken);
        return oraclePool.getConnection(reqLabels);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return oraclePool.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        oraclePool.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        oraclePool.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return oraclePool.getLoginTimeout();
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return iface.cast(this);
        }
        return oraclePool.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return true;
        }
        return oraclePool.isWrapperFor(iface);
    }

    String getUser() {
        return oraclePool.getUser();
    }

    String getUrl() {
        return oraclePool.getURL();
    }
}
