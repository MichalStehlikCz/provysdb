package com.provys.db.sqlquery.query;

import com.provys.common.exception.InternalException;
import com.provys.db.query.elements.ColumnExpression;
import com.provys.db.query.elements.SelectColumn;
import org.checkerframework.checker.nullness.qual.Nullable;

public class DefaultSelectColumnSqlBuilder implements SelectColumnSqlBuilder<SqlBuilder<?>> {

  private static final DefaultExpressionSqlBuilder INSTANCE = new DefaultExpressionSqlBuilder(
      ExpressionBindSqlBuilder.getInstance(),
      ExpressionColumnSqlBuilder.getInstance(),
      ExpressionFunctionSqlBuilder.getInstance(),
      LiteralSqlBuilder.getInstance(),
      LiteralNVarcharSqlBuilder.getInstance()
  );

  /**
   * Access default instance of this builder.
   *
   * @return singleton instance
   */
  public static DefaultExpressionSqlBuilder getInstance() {
    return INSTANCE;
  }

  private final SelectColumnTypeSqlBuilder<? super SqlBuilder<?>, ? super ColumnExpression<?>>
      columnExpression;

  public DefaultSelectColumnSqlBuilder(
      SelectColumnTypeSqlBuilder<? super SqlBuilder<?>, ? super ColumnExpression<?>>
          columnExpression) {
    this.columnExpression = columnExpression;
  }

  /**
   * Value of field columnExpression.
   *
   * @return value of field columnExpression
   */
  public SelectColumnTypeSqlBuilder<? super SqlBuilder<?>, ? super ColumnExpression<?>>
  getColumnExpression() {
    return columnExpression;
  }

  @Override
  public void append(SqlBuilder<?> sqlBuilder, SelectColumn<?> column) {
    if (column instanceof ColumnExpression) {
      columnExpression.append(sqlBuilder, (ColumnExpression<?>) column);
    } else {
      throw new InternalException("Column " + column + " not supported by SelectColumnSqlBuilder");
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultSelectColumnSqlBuilder that = (DefaultSelectColumnSqlBuilder) o;
    return columnExpression.equals(that.columnExpression);
  }

  @Override
  public int hashCode() {
    return columnExpression.hashCode();
  }

  @Override
  public String toString() {
    return "DefaultSelectColumnSqlBuilder{"
        + "columnExpression=" + columnExpression
        + '}';
  }
}
