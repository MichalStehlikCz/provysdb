package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Select;

public class SqlBuilderT extends SqlBuilderBase<DefaultStatementFactory, Select, SelectStatement> {

  SqlBuilderT(DefaultStatementFactory factory, Select query) {
    super(factory, query);
  }

  @Override
  public SelectStatement build() {
    return null;
  }
}
