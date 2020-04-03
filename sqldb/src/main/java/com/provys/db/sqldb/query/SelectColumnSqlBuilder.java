package com.provys.db.sqldb.query;

import com.provys.db.query.elements.SelectColumn;

public interface SelectColumnSqlBuilder<F extends StatementFactory, T extends SelectColumn<?>>
    extends ElementSqlBuilder<F, T> {

}
