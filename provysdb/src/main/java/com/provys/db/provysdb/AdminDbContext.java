package com.provys.db.provysdb;

import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.SqlTypeHandler;
import com.provys.db.defaultdb.types.SqlTypeMap;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class supports connection to Provys database as strong user (without switching Provys user) and
 * gives access to this database via connection (class {@code DbConnection}). Simplified wrapper
 * around actual data source, wraps retrieved connections in ProvysConnection wrapper, potentially
 * providing monitoring for database calls
 *
 * @author stehlik
 */
public final class AdminDbContext extends ProvysDbContext {

  /**
   * Creator for Provys admin database context.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   * @param sqlTypeHandler is sql type handler to be used with this data source
   */
  public AdminDbContext(ProvysConnectionPoolDataSource provysDataSource,
      SqlTypeHandler sqlTypeHandler) {
    super(provysDataSource, sqlTypeHandler);
  }

  /**
   * Creator for Provys admin database context; uses default type map.
   *
   * @param provysDataSource is DataSource used to access Provys Oracle database
   */
  public AdminDbContext(ProvysConnectionPoolDataSource provysDataSource) {
    this(provysDataSource, SqlTypeMap.getDefault());
  }

  @Override
  public DtUid getProvysUserId() {
    return getProvysDataSource().getProvysUserId();
  }

  @Override
  protected Connection getConnectionInt() throws SQLException {
    return getProvysDataSource().getConnection();
  }

  @Override
  public String toString() {
    return "AdminDbContext{" + super.toString() + '}';
  }
}
