package com.provys.db.provysdb;

import com.provys.auth.api.UserData;
import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.SqlException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Optional;
import java.util.Properties;
import oracle.ucp.UniversalConnectionPoolException;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * {@code DataSource} to be used for accessing PROVYS database. Based on Oracle Universal Connection
 * Pool, this data-source supports switching of session context to one representing user session on
 * retrieval
 *
 * <p>HikariCP was considered, but not chosen for now as it does not support connection labeling;
 * if there are any problems with Oracle UCP, we might switch to HikariCP or other connection pool.
 * Nothing outside this class should depend on used connection pool
 *
 * @author stehlik
 */
public class ProvysConnectionPoolDataSourceImpl implements ProvysConnectionPoolDataSource {

  private static final Logger LOG = LogManager.getLogger(ProvysConnectionPoolDataSourceImpl.class);
  private static final String POOL_NAME = "ProvysDB";

  private final PoolDataSource oraclePool;
  /**
   * UserId of user, corresponding to Oracle account used by connection pool. Usually filled in in
   * constructor, but might remain null if connection attempt in constructor failed and in that
   * case, it is retrieved when queried.
   */
  private @MonotonicNonNull DtUid provysUserId;

  /**
   * Check connection and retrieve Id of user, used to initialize connection by connection pool.
   *
   * @param oraclePool is connection pool that should be used to retrieve connection
   * @return Provys UserId corresponding to Oracle user, used to connect by pool. Null when
   *     connection fails
   */
  private static Optional<DtUid> checkConnection(PoolDataSource oraclePool) {
    try (Connection conn = oraclePool.getConnection()) {
      try (var callableStatement = conn.prepareCall(
          "BEGIN\n"
              + "  :c_User_ID:=KER_User_EP.mfw_GetUserID;\n"
              + "END;")) {
        callableStatement.registerOutParameter("c_User_ID", Types.NUMERIC);
        callableStatement.execute();
        LOG.info("Verified connection to database (user {}, db {})", oraclePool.getUser(),
            oraclePool.getURL());
        return Optional.of(DtUid.valueOf(callableStatement.getBigDecimal("c_User_ID")));
      }
    } catch (SQLException e) {
      LOG.warn(
          "Failed to verify connection pool (user {}, db {}}) - attempt to get"
              + " connection thrown {}", oraclePool::getUser, oraclePool::getURL, e::getMessage);
    }
    return Optional.empty();
  }

  /**
   * Constructor for provys connection that reads all info from environment. Creates supporting
   * Oracle Universal Connection Pool based on read connection information.
   *
   * @param dbConfiguration is class containing information, needed for configuration of database
   *                        pool
   */
  public ProvysConnectionPoolDataSourceImpl(ProvysDbConfiguration dbConfiguration) {
    try {
      var mgr = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager();
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
      oraclePool.setConnectionLabelingHighCost(ProvysConnectionLabelingCallback.NEW_CONNECTION + 1);
      oraclePool.setHighCostConnectionReuseThreshold(dbConfiguration.getConnectionReuseThreshold());
      oraclePool.setValidateConnectionOnBorrow(dbConfiguration.isValidateOnBorrow());
      oraclePool.setSecondsToTrustIdleConnection(dbConfiguration.getValidateSkipUntil());
      // Register connection labeling callback
      oraclePool.registerConnectionLabelingCallback(new ProvysConnectionLabelingCallback());
      LOG.info("Connection pool created (user {}, db {}, minsize {}, maxsize {}", user, db,
          minPoolSize,
          maxPoolSize);
    } catch (SQLException e) {
      throw new SqlException("Failed to create connection pool (user " + user + ", db " + db + ')',
          e);
    }
    // now try to get connection (to verify that connection pool parameters are valid)
    checkConnection(oraclePool).ifPresent(value -> this.provysUserId = value);
  }

  @Override
  public Connection getConnection() throws SQLException {
    return oraclePool.getConnection();
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    throw new SQLFeatureNotSupportedException();
  }

  @Override
  public Connection getConnectionForUser(UserData userData) throws SQLException {
    var reqLabels = new Properties();
    reqLabels.setProperty(ProvysConnectionLabelingCallback.PROPERTY_TOKEN,
        userData.getDbToken().getValue());
    return oraclePool.getConnection(reqLabels);
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
  public String getUrl() {
    return oraclePool.getURL();
  }

  @Override
  public String getUser() {
    return oraclePool.getUser();
  }

  @Override
  public DtUid getProvysUserId() {
    if (provysUserId == null) {
      // usually, it is initialized on constructor; potentially, we might call check connection
      // multiple times as we do not synchronize and mark variable as volatile, but cost associated
      // with this modifier is higher than gain in case it was not properly initialized and we
      // calculate it multiple times
      this.provysUserId = checkConnection(oraclePool).orElseThrow(
          () -> new SqlException(
              "Unable to retrieve Provys UserId from connection pool " + oraclePool));
    }
    return provysUserId;
  }

  @Override
  public String toString() {
    return "ProvysConnectionPoolDataSourceImpl{"
        + "oraclePool=" + oraclePool
        + '}';
  }
}
