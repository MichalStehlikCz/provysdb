package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT2;
import com.provys.db.querybuilder.ExpressionBuilder;
import com.provys.db.sqlquery.query.SelectStatementT2;

public interface DbSelectBuilderT2<T1, T2> extends
    DbSelectBuilderBase<SelectStatementT2<T1, T2>, SelectT2<T1, T2>, DbSelectBuilderT2<T1, T2>> {

  /**
   * Add column to select builder.
   *
   * @param column is column to be added
   * @return builder with added column
   */
  @Override
  <T3> DbSelectBuilder column(SelectColumn<T3> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T3> DbSelectBuilder column(ExpressionBuilder<T3> column) {
    return column(column.buildColumn());
  }
}
