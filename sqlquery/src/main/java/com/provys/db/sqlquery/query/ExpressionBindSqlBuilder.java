package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.ExpressionBind;

final class ExpressionBindSqlBuilder
    implements ExpressionTypeSqlBuilder<SqlBuilder<?>, ExpressionBind<?>> {

  private static final ExpressionBindSqlBuilder INSTANCE = new ExpressionBindSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static ExpressionBindSqlBuilder getInstance() {
    return INSTANCE;
  }

  private ExpressionBindSqlBuilder() {
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<ExpressionBind<?>> getType() {
    return (Class<ExpressionBind<?>>) (Class<?>) ExpressionBind.class;
  }

  @Override
  public void append(SqlBuilder<?> sqlBuilder, ExpressionBind<?> element) {
    sqlBuilder
        .append('?')
        .addBind(element.getBindVariable());
  }

  @Override
  public String toString() {
    return "ExpressionBindSqlBuilder{}";
  }
}
