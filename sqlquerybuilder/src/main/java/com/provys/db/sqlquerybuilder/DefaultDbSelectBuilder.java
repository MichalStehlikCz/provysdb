package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.querybuilder.SelectBuilder;
import com.provys.db.sqlquery.query.SelectStatement;
import com.provys.db.sqlquery.query.StatementFactory;

final class DefaultDbSelectBuilder
    extends DefaultDbSelectBuilderT<SelectBuilder, DefaultDbSelectBuilder>
    implements DbSelectBuilder {

  DefaultDbSelectBuilder(SelectBuilder selectBuilder, StatementFactory statementFactory) {
    super(selectBuilder, statementFactory);
  }

  @Override
  protected DefaultDbSelectBuilder self() {
    return this;
  }

  @Override
  protected DefaultDbSelectBuilder clone(SelectBuilder newSelectBuilder) {
    return new DefaultDbSelectBuilder(newSelectBuilder, getStatementFactory());
  }

  @Override
  public <T1> DbSelectBuilder column(SelectColumn<T1> column) {
    return withSelectBuilder(getSelectBuilder().column(column));
  }

  @Override
  public Select buildSelect() {
    return getSelectBuilder().build();
  }

  @Override
  public SelectStatement build() {
    return getStatementFactory().getSelect(buildSelect());
  }

  @Override
  public String toString() {
    return "DefaultDbSelectBuilder{" + super.toString() + '}';
  }
}
