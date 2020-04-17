package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.querybuilder.SelectBuilderT0;
import com.provys.db.sqlquery.query.StatementFactory;

final class DefaultDbSelectBuilderT0
    extends DefaultDbSelectBuilderT<SelectBuilderT0, DefaultDbSelectBuilderT0>
    implements DbSelectBuilderT0 {

  DefaultDbSelectBuilderT0(SelectBuilderT0 selectBuilder,
      StatementFactory statementFactory) {
    super(selectBuilder, statementFactory);
  }

  @Override
  protected DefaultDbSelectBuilderT0 self() {
    return this;
  }

  @Override
  protected DefaultDbSelectBuilderT0 clone(SelectBuilderT0 newSelectBuilder) {
    return new DefaultDbSelectBuilderT0(newSelectBuilder, getStatementFactory());
  }

  @Override
  public <T1> DbSelectBuilderT1<T1> column(SelectColumn<T1> column) {
    return new DefaultDbSelectBuilderT1<>(getSelectBuilder().column(column), getStatementFactory());
  }

  @Override
  public String toString() {
    return "DefaultDbSelectBuilderT0{" + super.toString() + '}';
  }
}
