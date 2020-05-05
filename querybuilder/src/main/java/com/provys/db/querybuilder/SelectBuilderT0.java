package com.provys.db.querybuilder;

import com.provys.db.query.elements.SelectColumn;

/**
 * Select builder with no defined projection (columns). Without projection, no select statement
 * can be produced and thus build is not available
 */
public interface SelectBuilderT0 extends SelectBuilderT<SelectBuilderT0>  {

  /**
   * Add column to select builder.
   *
   * @param column is column to be added
   * @param <T1> is type of added column
   * @return builder with added column
   */
  @Override
  <T1> SelectBuilderT1<T1> column(SelectColumn<T1> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T1> SelectBuilderT1<T1> column(ExpressionBuilder<T1> column) {
    return column(column.buildColumn());
  }
}
