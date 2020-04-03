package com.provys.db.sqldb.query;

import com.provys.db.query.elements.ExpressionBind;

final class ExpressionBindSqlBuilder implements
    ExpressionSqlBuilder<StatementFactory<?>, ExpressionBind<?>> {

  private static final ExpressionBindSqlBuilder INSTANCE = new ExpressionBindSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static ExpressionBindSqlBuilder getInstance() {
    return INSTANCE;
  }

  /**
   * Return singleton instance, retyped to suite given factory type. Exploits fact that
   * ExpressionBindSqlBuilder's factory parameter is covariant.
   *
   * @param factoryType is type of factory this builder should be suitable for
   * @param <F>         is type parameter identifying factory type
   * @return singleton instance, retyped to desired parametrisation
   */
  @SuppressWarnings("unchecked")
  public static <F extends StatementFactory<?>> ExpressionSqlBuilder<F, ExpressionBind<?>> getInstance(
      Class<F> factoryType) {
    return (ExpressionSqlBuilder<F, ExpressionBind<?>>) INSTANCE;
  }

  private ExpressionBindSqlBuilder() {
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<ExpressionBind<?>> getType() {
    return (Class<ExpressionBind<?>>) (Class<?>) ExpressionBind.class;
  }

  @Override
  public void append(SqlBuilder<? extends StatementFactory<?>, ?, ?> sqlBuilder,
      ExpressionBind<?> element) {
    sqlBuilder.getCodeBuilder()
        .append('?')
        .addBind(element.getBindVariable());
  }

  @Override
  public String toString() {
    return "ExpressionBindBuilder{}";
  }
}
