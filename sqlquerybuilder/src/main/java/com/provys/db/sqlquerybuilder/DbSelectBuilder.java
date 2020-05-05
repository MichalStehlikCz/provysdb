package com.provys.db.sqlquerybuilder;

import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.querybuilder.ExpressionBuilder;
import com.provys.db.sqlquery.query.SelectStatement;

/**
 * Builder class for {link SelectStatement} / {@link Select}, allows step-by-step construction of
 * whole select statement.
 */
public interface DbSelectBuilder extends
    DbSelectBuilderBase<SelectStatement, Select, DbSelectBuilder> {

  /**
   * Add column to select builder. Might modify builder and return self or create new builder.
   *
   * @param column is column to be added
   * @return builder with added column
   */
  @Override
  <T1> DbSelectBuilder column(SelectColumn<T1> column);

  /**
   * Add column based on expression to select builder. Might modify builder and return self or
   * create new builder.
   *
   * @param column is expression to be added as column
   * @return builder with added column
   */
  @Override
  default <T1> DbSelectBuilder column(ExpressionBuilder<T1> column) {
    return column(column.buildColumn());
  }
}
