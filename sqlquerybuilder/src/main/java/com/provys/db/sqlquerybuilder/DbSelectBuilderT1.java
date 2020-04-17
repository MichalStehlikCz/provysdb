package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.querybuilder.ExpressionBuilder;
import com.provys.db.sqlquery.query.SelectStatementT1;

public interface DbSelectBuilderT1<T1> extends
    DbSelectBuilderBase<SelectStatementT1<T1>, SelectT1<T1>, DbSelectBuilderT1<T1>> {

  /**
   * Add column to select builder.
   *
   * @param column is column to be added
   * @param <T2> is type of added column
   * @return builder with added column
   */
  @Override
  <T2> DbSelectBuilderT2<T1, T2> column(SelectColumn<T2> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T2> DbSelectBuilderT2<T1, T2> column(ExpressionBuilder<T2> column) {
    return column(column.buildColumn());
  }
}
