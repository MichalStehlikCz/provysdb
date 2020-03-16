package com.provys.provysdb.sqldefault;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.provysdb.sql.SqlContext;
import com.provys.provysdb.sql.SqlFactory;

/**
 * Context used when no database connection exists. ANy attempt to access database will obviously
 * fail.
 */
public class SqlContextDefault implements SqlContext {

  @Override
  public SqlFactory getSqlFactory() {
    return SqlFactoryDefault.getInstance();
  }

  @Override
  public DbConnection getConnection() {
    throw new InternalException("Cannot retrieve connection from no-db sql context");
  }

  @Override
  public DbConnection getConnection(String dbToken) {
    throw new InternalException("Cannot retrieve connection from no-db sql context");
  }

  @Override
  public String getUser() {
    return "NoUser";
  }

  @Override
  public String getUrl() {
    return "NoDb";
  }

  @Override
  public SqlTypeMap getSqlTypeMap() {
    return ;
  }
}
