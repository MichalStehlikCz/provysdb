package com.provys.provysdb.dbcontext;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provys descendant to {@code Statement} interface. Returns Provys specific result set, that allows
 * easier access to column values using Provys datatypes
 */
public interface DbStatement extends Statement {

  @Override
  DbResultSet executeQuery(String sql) throws SQLException;

  @Override
  DbResultSet getResultSet() throws SQLException;

  @Override
  DbResultSet getGeneratedKeys() throws SQLException;
}
