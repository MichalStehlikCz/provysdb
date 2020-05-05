package com.provys.db.querybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT2;

public interface SelectBuilderT2<T1, T2>
    extends SelectBuilderBase<SelectT2<T1, T2>, SelectBuilderT2<T1, T2>> {

  /**
   * Add column to select builder.
   *
   * @param column is column to be added
   * @return builder with added column
   */
  @Override
  <T3> SelectBuilder column(SelectColumn<T3> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T3> SelectBuilder column(ExpressionBuilder<T3> column) {
    return column(column.buildColumn());
  }
}
