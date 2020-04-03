package com.provys.db.sqldb.query;

import com.provys.db.query.elements.SelectClause;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

public interface SelectClauseSqlBuilder<F extends StatementFactory, T extends SelectClause>
    extends ElementSqlBuilder<F, T> {

}