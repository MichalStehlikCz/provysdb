package com.provys.provysdb.dbcontext;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provys descendant to {@code Statement} interface.
 */
public interface ProvysStatement extends Statement {

    @Override
    ProvysResultSet executeQuery(String sql) throws SQLException;

    @Override
    ProvysResultSet getResultSet() throws SQLException;

    @Override
    ProvysConnection getConnection() throws SQLException;

    @Override
    ProvysResultSet getGeneratedKeys() throws SQLException;
}
