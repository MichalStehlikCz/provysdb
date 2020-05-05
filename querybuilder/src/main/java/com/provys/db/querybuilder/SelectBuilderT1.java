package com.provys.db.querybuilder;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.elements.SelectT1;

public interface SelectBuilderT1<T1> extends SelectBuilderBase<SelectT1<T1>, SelectBuilderT1<T1>> {

  /**
   * Add column to select builder.
   *
   * @param column is column to be added
   * @param <T2> is type of added column
   * @return builder with added column
   */
  @Override
  <T2> SelectBuilderT2<T1, T2> column(SelectColumn<T2> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T2> SelectBuilderT2<T1, T2> column(ExpressionBuilder<T2> column) {
    return column(column.buildColumn());
  }
}
