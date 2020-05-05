package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.querybuilder.SelectBuilderT1;
import com.provys.db.sqlquery.query.SelectStatementT1;
import com.provys.db.sqlquery.query.StatementFactory;

final class DefaultDbSelectBuilderT1<T1>
    extends DefaultDbSelectBuilderT<SelectBuilderT1<T1>, DefaultDbSelectBuilderT1<T1>>
    implements DbSelectBuilderT1<T1> {

  DefaultDbSelectBuilderT1(SelectBuilderT1<T1> selectBuilder,
      StatementFactory statementFactory) {
    super(selectBuilder, statementFactory);
  }

  @Override
  protected DefaultDbSelectBuilderT1<T1> self() {
    return this;
  }

  @Override
  protected DefaultDbSelectBuilderT1<T1> clone(SelectBuilderT1<T1> newSelectBuilder) {
    return new DefaultDbSelectBuilderT1<>(newSelectBuilder, getStatementFactory());
  }

  @Override
  public <T2> DbSelectBuilderT2<T1, T2> column(SelectColumn<T2> column) {
    return new DefaultDbSelectBuilderT2<>(getSelectBuilder().column(column), getStatementFactory());
  }

  @Override
  public SelectT1<T1> buildSelect() {
    return getSelectBuilder().build();
  }

  @Override
  public SelectStatementT1<T1> build() {
    return getStatementFactory().getSelect(buildSelect());
  }

  @Override
  public String toString() {
    return "DefaultDbSelectBuilderT1{" + super.toString() + '}';
  }
}
