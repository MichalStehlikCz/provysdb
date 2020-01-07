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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

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
@ApplicationScoped
public class ProvysConnectionPoolDataSourceImpl implements ProvysConnectionPoolDataSource {
    @Nonnull
    private static final Logger LOG = LogManager.getLogger(ProvysConnectionPoolDataSourceImpl.class);
    @Nonnull
    private static final String POOL_NAME = "ProvysDB";

    @Nonnull
    private final PoolDataSource oraclePool;

    /**
     * Constructor for provys connection that reads all info from environment.
     * Creates supporting Oracle Universal Connection Pool based on read connection information.
     */
    @Inject
    public ProvysConnectionPoolDataSourceImpl(ProvysDbConfiguration dbConfiguration) {
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
            user = dbConfiguration.getUser();
            oraclePool.setUser(user);
            String pwd = dbConfiguration.getPwd();
            oraclePool.setPassword(pwd);
            db = dbConfiguration.getUrl();
            oraclePool.setURL("jdbc:oracle:thin:@" + db);
            oraclePool.setConnectionPoolName(POOL_NAME);
            int minPoolSize = dbConfiguration.getMinPoolSize();
            oraclePool.setMinPoolSize(minPoolSize);
            oraclePool.setInitialPoolSize(minPoolSize);
            int maxPoolSize = dbConfiguration.getMaxPoolSize();
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

    @Override
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

    @Override
    public String getUser() {
        return oraclePool.getUser();
    }

    @Override
    public String getUrl() {
        return oraclePool.getURL();
    }
}
