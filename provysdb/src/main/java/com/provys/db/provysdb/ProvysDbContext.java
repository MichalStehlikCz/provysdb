package com.provys.db.provysdb;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlException;
import com.provys.db.dbcontext.SqlTypeHandler;
import com.provys.db.defaultdb.dbcontext.DefaultConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Common ancestor for admin and user database context. Holds connection pool and type handler.
 */
public abstract class ProvysDbContext implements DbContext {

  private final ProvysConnectionPoolDataSource provysDataSource;
  private final SqlTypeHandler sqlTypeHandler;

  public ProvysDbContext(
      ProvysConnectionPoolDataSource provysDataSource, SqlTypeHandler sqlTypeHandler) {
    this.provysDataSource = provysDataSource;
    this.sqlTypeHandler = sqlTypeHandler;
  }

  /**
   * Gives access to connection pool.
   *
   * @return value of field provysDataSource
   */
  protected ProvysConnectionPoolDataSource getProvysDataSource() {
    return provysDataSource;
  }

  @Override
  public String getUrl() {
    return provysDataSource.getUrl();
  }

  @Override
  public String getUser() {
    return provysDataSource.getUser();
  }

  /**
   * Get connection from underlying data source. Retrieved connection is only used internally,
   * it is wrapped in DbConnection in public getConnection method.
   *
   * @return connection to be used internally
   * @throws SQLException when connection retrieval from pool thrown this exception
   */
  protected abstract Connection getConnectionInt() throws SQLException;

  @Override
  public DbConnection getConnection() {
    try {
      return new DefaultConnection(getConnectionInt(), getSqlTypeHandler());
    } catch (SQLException e) {
      throw new SqlException("Failed to initialize connection", e);
    }
  }

  @Override
  public SqlTypeHandler getSqlTypeHandler() {
    return sqlTypeHandler;
  }

  @Override
  public String toString() {
    return "ProvysDbContext{"
        + "provysDataSource=" + provysDataSource
        + ", sqlTypeHandler=" + sqlTypeHandler
        + '}';
  }
}
