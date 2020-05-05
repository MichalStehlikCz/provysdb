package com.provys.db.provysdb;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlException;
import com.provys.db.dbcontext.SqlTypeHandler;
import com.provys.db.defaultdb.dbcontext.DefaultConnection;
import com.provys.db.defaultdb.types.SqlTypeMap;
import java.sql.SQLException;

/**
 * Class supports connection to Provys database as strong user (without switching Provys user) and
 * gives access to this database via connection (class {@code DbConnection}). Simplified wrapper
 * around actual data source, wraps retrieved connections in ProvysConnection wrapper, potentially
 * providing monitoring for database calls
 *
 * @author stehlik
 */
public final class AdminDbContext implements DbContext {

  private final ProvysConnectionPoolDataSource provysDataSource;
  private final SqlTypeHandler sqlTypeHandler;

  /**
   * Creator for Provys admin database context.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   * @param sqlTypeHandler is sql type handler to be used with this data source
   */
  public AdminDbContext(ProvysConnectionPoolDataSource provysDataSource,
      SqlTypeHandler sqlTypeHandler) {
    this.provysDataSource = provysDataSource;
    this.sqlTypeHandler = sqlTypeHandler;
  }

  /**
   * Creator for Provys admin database context; uses default type map.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   */
  public AdminDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    this(provysDataSource, SqlTypeMap.getDefaultMap());
  }

  @Override
  public DbConnection getConnection() {
    try {
      return new DefaultConnection(provysDataSource.getConnection(), getSqlTypeHandler());
    } catch (SQLException e) {
      throw new SqlException("Failed to initialize connection", e);
    }
  }

  @Override
  public String getUser() {
    return provysDataSource.getUser();
  }

  @Override
  public String getUrl() {
    return provysDataSource.getUrl();
  }

  @Override
  public SqlTypeHandler getSqlTypeHandler() {
    return sqlTypeHandler;
  }

  @Override
  public String toString() {
    return "AdminDbContext{"
        + "provysDataSource=" + provysDataSource
        + ", sqlTypeHandler=" + sqlTypeHandler
        + '}';
  }
}
