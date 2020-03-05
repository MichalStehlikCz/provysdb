package com.provys.provysdb.dbcontext.impl;

import com.provys.common.exception.RegularException;
import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import com.provys.provysdb.dbcontext.type.SqlTypeFactory;
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
  private final SqlTypeMap sqlTypeMap;

  /**
   * Default creator for Provys database context.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   */
  public ProvysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    this.provysDataSource = Objects.requireNonNull(provysDataSource);
    sqlTypeMap = new SqlTypeFactory().getDefaultMap();
  }

  @Override
  public DbConnection getConnection() {
    try {
      return new ProvysConnection(provysDataSource.getConnection(), sqlTypeMap);
    } catch (SQLException e) {
      throw new RegularException("PROVYSDB_CANNOTCONNECT", "Failed to initialize connection", e);
    }
  }

  @Override
  public DbConnection getConnection(String dbToken) {
    try {
      return new ProvysConnection(
          provysDataSource.getConnectionWithToken(Objects.requireNonNull(dbToken)),
          sqlTypeMap);
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
  public SqlTypeMap getSqlTypeMap() {
    return sqlTypeMap;
  }

  @Override
  public String toString() {
    return "ProvysDbContext{"
        + "provysDataSource=" + provysDataSource
        + ", sqlTypeMap=" + sqlTypeMap
        + '}';
  }
}
