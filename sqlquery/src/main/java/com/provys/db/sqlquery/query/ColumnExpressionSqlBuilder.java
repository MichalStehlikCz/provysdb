package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.ColumnExpression;

/**
 * Class builds sql text for {@link ColumnExpression}.
 */
public final class ColumnExpressionSqlBuilder
    implements SelectColumnTypeSqlBuilder<SqlBuilder<?>, ColumnExpression<?>> {

  private static final ColumnExpressionSqlBuilder INSTANCE = new ColumnExpressionSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static ColumnExpressionSqlBuilder getInstance() {
    return INSTANCE;
  }

  private ColumnExpressionSqlBuilder() {
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<ColumnExpression<?>> getType() {
    return (Class<ColumnExpression<?>>) (Class<?>) ColumnExpression.class;
  }

  @Override
  public void append(SqlBuilder<?> sqlBuilder, ColumnExpression<?> element) {
    sqlBuilder.append(element.getExpression());
    var alias = element.getAlias();
    if (alias != null) {
      sqlBuilder
          .append(' ')
          .append(alias);
    }
  }

  @Override
  public String toString() {
    return "ColumnExpressionSqlBuilder{}";
  }
}
