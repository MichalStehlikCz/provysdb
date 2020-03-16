package com.provys.db.sqldb.sql;

import com.provys.db.sql.SelectClause;

/**
 * Specialisation of select clause (most often list of columns) to be used when preparing sql
 * text.
 */
public interface SqlSelectClause extends SelectClause, SqlElement {

}
