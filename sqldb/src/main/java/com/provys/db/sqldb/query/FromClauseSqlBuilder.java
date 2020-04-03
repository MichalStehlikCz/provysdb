package com.provys.db.sqldb.query;

import com.provys.db.query.elements.FromClause;

public interface FromClauseSqlBuilder<F extends StatementFactory, T extends FromClause> extends
    ElementSqlBuilder<F, T> {

}
