package com.provys.db.sqldb.query;

import com.provys.db.query.elements.ExpressionColumn;

public final class ExpressionColumnSqlBuilder
    implements ExpressionSqlBuilder<StatementFactory<?>, ExpressionColumn<?>> {

  private static final ExpressionColumnSqlBuilder INSTANCE = new ExpressionColumnSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static ExpressionColumnSqlBuilder getInstance() {
    return INSTANCE;
  }

  private ExpressionColumnSqlBuilder() {
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<ExpressionColumn<?>> getType() {
    return (Class<ExpressionColumn<?>>) (Class<?>) ExpressionColumn.class;
  }

  @Override
  public void append(SqlBuilder<? extends StatementFactory<?>, ?, ?> sqlBuilder,
      ExpressionColumn<?> element) {
    var builder = sqlBuilder.getCodeBuilder();
    element.getOptTable().ifPresent(table -> builder.append(table).append('.'));
    builder.append(element.getColumn());
  }

  @Override
  public String toString() {
    return "ExpressionColumnSqlBuilder{}";
  }
}
