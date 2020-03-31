package com.provys.provysdb.dbsqlbuilder;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.SqlTypeMap;
import sqlbuilder.Sql;

/**
 * Interface defining root factory class for all objects, used for building Sql statements.
 */
public interface DbSql extends Sql {

  /**
   * Connection to underlying datasource.
   *
   * @return connection to underlying datasource
   */
  DbConnection getConnection();

  /**
   * Used Sql type adapter map.
   *
   * @return used com.provys.db.sql type adapter map (taken from underlying datasource)
   */
  SqlTypeMap getSqlTypeMap();

  /**
   * Select statement builder.
   *
   * @return select statement builder
   */
  DbSelectBuilderT0 select();
}
