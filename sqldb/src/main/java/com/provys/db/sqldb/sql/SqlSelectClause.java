package com.provys.db.sqldb.sql;

import com.provys.db.query.SelectClause;

/**
 * Specialisation of select clause (most often list of columns) to be used when preparing com.provys.db.sql
 * text.
 */
public interface SqlSelectClause extends SelectClause, SqlElement {

}
