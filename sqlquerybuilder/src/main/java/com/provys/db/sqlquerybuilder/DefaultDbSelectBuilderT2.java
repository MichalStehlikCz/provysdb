package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT2;
import com.provys.db.querybuilder.SelectBuilderT2;
import com.provys.db.sqlquery.query.SelectStatementT2;
import com.provys.db.sqlquery.query.StatementFactory;

class DefaultDbSelectBuilderT2<T1, T2>
    extends DefaultDbSelectBuilderT<SelectBuilderT2<T1, T2>, DefaultDbSelectBuilderT2<T1, T2>>
    implements DbSelectBuilderT2<T1, T2> {

  DefaultDbSelectBuilderT2(SelectBuilderT2<T1, T2> selectBuilder,
      StatementFactory statementFactory) {
    super(selectBuilder, statementFactory);
  }

  @Override
  protected DefaultDbSelectBuilderT2<T1, T2> self() {
    return this;
  }

  @Override
  protected DefaultDbSelectBuilderT2<T1, T2> clone(SelectBuilderT2<T1, T2> newSelectBuilder) {
    return new DefaultDbSelectBuilderT2<>(newSelectBuilder, getStatementFactory());
  }

  @Override
  public <T3> DbSelectBuilder column(SelectColumn<T3> column) {
    return new DefaultDbSelectBuilder(getSelectBuilder().column(column), getStatementFactory());
  }

  @Override
  public SelectT2<T1, T2> buildSelect() {
    return getSelectBuilder().build();
  }

  @Override
  public SelectStatementT2<T1, T2> build() {
    return getStatementFactory().getSelect(buildSelect());
  }

  @Override
  public String toString() {
    return "DefaultDbSelectBuilderT2{" + super.toString() + '}';
  }
}
