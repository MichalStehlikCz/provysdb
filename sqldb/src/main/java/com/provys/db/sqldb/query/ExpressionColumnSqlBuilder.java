package com.provys.db.sqldb.query;

import com.provys.db.query.elements.ExpressionColumn;

public final class ExpressionColumnSqlBuilder
    implements ExpressionSqlBuilder<SqlBuilder<?>, ExpressionColumn<?>> {

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

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context this statement should be appended to
   * @param element    is element that is being appended
   */
  @Override
  public void append(SqlBuilder<?> sqlBuilder, ExpressionColumn<?> element) {
    element.getOptTable().ifPresent(table -> sqlBuilder.append(table).append('.'));
    sqlBuilder.append(element.getColumn());
  }

  @Override
  public String toString() {
    return "ExpressionColumnSqlBuilder{}";
  }
}
