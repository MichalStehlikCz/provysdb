package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.SelectColumn;

public interface SelectColumnTypeSqlBuilder<B extends SqlBuilder<?>, T extends SelectColumn<?>>
    extends ElementTypeSqlBuilder<B, T> {

}
