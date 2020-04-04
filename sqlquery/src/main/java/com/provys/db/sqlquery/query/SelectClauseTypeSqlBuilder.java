package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.SelectClause;

public interface SelectClauseTypeSqlBuilder<B extends SqlBuilder<?>, T extends SelectClause>
    extends ElementTypeSqlBuilder<B, T> {

}