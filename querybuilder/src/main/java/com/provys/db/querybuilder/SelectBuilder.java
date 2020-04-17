package com.provys.db.querybuilder;

import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectColumn;

/**
 * Builder class for {@link Select}, allows step-by-step construction of whole select statement.
 */
public interface SelectBuilder extends SelectBuilderBase<Select, SelectBuilder> {

  /**
   * Add column to select builder. Might modify builder and return self or create new builder.
   *
   * @param column is column to be added
   * @return builder with added column
   */
  @Override
  <T1> SelectBuilder column(SelectColumn<T1> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T1> SelectBuilder column(ExpressionBuilder<T1> column) {
    return column(column.buildColumn());
  }
}
