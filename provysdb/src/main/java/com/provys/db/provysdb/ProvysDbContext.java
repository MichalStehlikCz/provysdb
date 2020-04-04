package com.provys.db.provysdb;

import com.provys.common.exception.RegularException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.sqlquery.dbcontext.NoDbContext;
import com.provys.db.sqlquery.dbcontext.DefaultConnection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class supports connection to Provys database and gives access to this database via connection
 * (class {@code DbConnection}). Simplified wrapper around actual data source, wraps retrieved
 * connections in ProvysConnection wrapper, potentially providing monitoring for database calls
 *
 * @author stehlik
 */
public class ProvysDbContext extends NoDbContext {

  private final ProvysConnectionPoolDataSource provysDataSource;

  /**
   * Default creator for Provys database context.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   */
  public ProvysDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    this.provysDataSource = Objects.requireNonNull(provysDataSource);
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
  public String toString() {
    return "ProvysDbContext{"
        + "provysDataSource=" + provysDataSource
        + ", " + super.toString() + '}';
  }
}
