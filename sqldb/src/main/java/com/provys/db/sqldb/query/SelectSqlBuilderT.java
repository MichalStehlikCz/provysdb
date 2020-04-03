package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Select;

public class SelectSqlBuilderT extends
    SelectSqlBuilderBase<DefaultStatementFactory, Select, SelectStatement> {

  SelectSqlBuilderT(DefaultStatementFactory factory, Select query) {
    super(factory, query);
  }

  @Override
  public SelectStatement build() {
    return null;
  }
}
