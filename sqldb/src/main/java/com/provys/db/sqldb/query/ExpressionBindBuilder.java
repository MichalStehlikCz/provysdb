package com.provys.db.sqldb.query;

import com.provys.db.query.elements.ExpressionBind;

final class ExpressionBindBuilder implements
    ExpressionBuilder<StatementFactory, ExpressionBind<?>> {

  private static final ExpressionBindBuilder INSTANCE = new ExpressionBindBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static ExpressionBindBuilder getInstance() {
    return INSTANCE;
  }

  private ExpressionBindBuilder() {
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<ExpressionBind<?>> getType() {
    return (Class<ExpressionBind<?>>) (Class<?>) ExpressionBind.class;
  }

  @Override
  public void append(SqlBuilder<? extends StatementFactory, ?, ?> sqlBuilder,
      ExpressionBind<?> expression) {
    sqlBuilder.getCodeBuilder()
        .append('?')
        .addBind(expression.getBindVariable());
  }

  @Override
  public String toString() {
    return "ExpressionBindBuilder{}";
  }
}
