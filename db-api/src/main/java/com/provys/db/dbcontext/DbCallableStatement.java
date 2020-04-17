package com.provys.db.dbcontext;

import java.sql.CallableStatement;

/**
 * Provys specific wrapper on {@link CallableStatement}. Adds logging and support for Provys
 * specific data types.
 */
public interface DbCallableStatement extends CallableStatement, DbPreparedStatement {

}
