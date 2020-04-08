package com.provys.db.provysdb;

import com.provys.common.exception.RegularException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeHandler;
import com.provys.db.defaultdb.dbcontext.DefaultConnection;
import com.provys.db.defaultdb.types.SqlTypeMap;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class supports connection to Provys database and gives access to this database via connection
 * (class {@code DbConnection}). Simplified wrapper around actual data source, wraps retrieved
 * connections in ProvysConnection wrapper, potentially providing monitoring for database calls
 *
 * @author stehlik
 */
public class ProvysDbContext implements DbContext {

  private final ProvysConnectionPoolDataSource provysDataSource;
  private final SqlTypeHandler sqlTypeHandler;

  /**
   * Creator for Provys database context.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   * @param sqlTypeHandler is sql type handler to be used with this data source
   */
  public ProvysDbContext(ProvysConnectionPoolDataSource provysDataSource,
      SqlTypeHandler sqlTypeHandler) {
    this.provysDataSource = provysDataSource;
    this.sqlTypeHandler = sqlTypeHandler;
  }

  /**
   * Creator for Provys database context; uses default type map.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   */
  public ProvysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    this(provysDataSource, SqlTypeMap.getDefaultMap());
  }

  @Override
  public DbConnection getConnection() {
    try {
      return new DefaultConnection(provysDataSource.getConnection(), getSqlTypeHandler());
    } catch (SQLException e) {
      throw new RegularException("PROVYSDB_CANNOTCONNECT", "Failed to initialize connection", e);
    }
  }

  @Override
  public DbConnection getConnection(String dbToken) {
    try {
      return new DefaultConnection(
          provysDataSource.getConnectionWithToken(Objects.requireNonNull(dbToken)),
          getSqlTypeHandler());
    } catch (SQLException e) {
      throw new RegularException("PROVYSDB_CANNOTCONNECTWITHTOKEN",
          "Failed to initialize connection with token", e);
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
    return "ProvysDbContext{"
        + "provysDataSource=" + provysDataSource
        + ", sqlTypeHandler=" + sqlTypeHandler
        + '}';
  }
}
