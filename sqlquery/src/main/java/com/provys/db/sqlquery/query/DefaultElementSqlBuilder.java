package com.provys.db.sqlquery.query;

import com.provys.common.exception.InternalException;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.Element;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.FromClause;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.Select;
import com.provys.db.query.elements.SelectClause;
import com.provys.db.query.elements.SelectColumn;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Implementation of {@link ElementSqlBuilder}, using map of element builders for individual types
 * of elements.
 *
 * @param <B> is type of builder this map should be usable for
 */
public class DefaultElementSqlBuilder<B extends SqlBuilder<?>> implements ElementSqlBuilder<B> {

  private final SelectSqlBuilder<? super B> selectSqlBuilder;
  private final SelectClauseSqlBuilder<? super B> selectClauseSqlBuilder;
  private final SelectColumnSqlBuilder<? super B> selectColumnSqlBuilder;
  private final FromClauseSqlBuilder<? super B> fromClauseSqlBuilder;
  private final FromElementSqlBuilder<? super B> fromElementSqlBuilder;
  private final ConditionSqlBuilder<? super B> conditionSqlBuilder;
  private final ExpressionSqlBuilder<? super B> expressionSqlBuilder;

  /**
   * Create element builder, using supplied builders for whole statement, select clause,
   * select columns, from clause, from elements, conditions and expressions.
   *
   * @param selectSqlBuilder will be used for select statements
   * @param selectClauseSqlBuilder will be used for select clause
   * @param selectColumnSqlBuilder will be used for select columns
   * @param fromClauseSqlBuilder will be used for from clause
   * @param fromElementSqlBuilder will be used for from elements
   * @param conditionSqlBuilder will be used for conditions
   * @param expressionSqlBuilder will be used for expressions
   */
  public DefaultElementSqlBuilder(
      SelectSqlBuilder<? super B> selectSqlBuilder,
      SelectClauseSqlBuilder<? super B> selectClauseSqlBuilder,
      SelectColumnSqlBuilder<? super B> selectColumnSqlBuilder,
      FromClauseSqlBuilder<? super B> fromClauseSqlBuilder,
      FromElementSqlBuilder<? super B> fromElementSqlBuilder,
      ConditionSqlBuilder<? super B> conditionSqlBuilder,
      ExpressionSqlBuilder<? super B> expressionSqlBuilder) {
    this.selectSqlBuilder = selectSqlBuilder;
    this.selectClauseSqlBuilder = selectClauseSqlBuilder;
    this.selectColumnSqlBuilder = selectColumnSqlBuilder;
    this.fromClauseSqlBuilder = fromClauseSqlBuilder;
    this.fromElementSqlBuilder = fromElementSqlBuilder;
    this.conditionSqlBuilder = conditionSqlBuilder;
    this.expressionSqlBuilder = expressionSqlBuilder;
  }

  @Override
  public void append(B sqlBuilder, Element<?> element) {
    if (element instanceof Select) {
      selectSqlBuilder.append(sqlBuilder, (Select) element);
    } else if (element instanceof SelectClause) {
      selectClauseSqlBuilder.append(sqlBuilder, (SelectClause) element);
    } else if (element instanceof SelectColumn) {
      selectColumnSqlBuilder.append(sqlBuilder, (SelectColumn<?>) element);
    } else if (element instanceof FromClause) {
      fromClauseSqlBuilder.append(sqlBuilder, (FromClause) element);
    } else if (element instanceof FromElement) {
      fromElementSqlBuilder.append(sqlBuilder, (FromElement) element);
    } else if (element instanceof Condition) {
      conditionSqlBuilder.append(sqlBuilder, (Condition) element);
    } else if (element instanceof Expression) {
      expressionSqlBuilder.append(sqlBuilder, (Expression<?>) element);
    } else {
      throw new InternalException("Element " + element + " not supported by ElementSqlBuilder");
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
    DefaultElementSqlBuilder<?> that = (DefaultElementSqlBuilder<?>) o;
    return selectSqlBuilder.equals(that.selectSqlBuilder)
        && selectClauseSqlBuilder.equals(that.selectClauseSqlBuilder)
        && selectColumnSqlBuilder.equals(that.selectColumnSqlBuilder)
        && fromClauseSqlBuilder.equals(that.fromClauseSqlBuilder)
        && fromElementSqlBuilder.equals(that.fromElementSqlBuilder)
        && conditionSqlBuilder.equals(that.conditionSqlBuilder)
        && expressionSqlBuilder.equals(that.expressionSqlBuilder);
  }

  @Override
  public int hashCode() {
    int result = selectSqlBuilder.hashCode();
    result = 31 * result + selectClauseSqlBuilder.hashCode();
    result = 31 * result + selectColumnSqlBuilder.hashCode();
    result = 31 * result + fromClauseSqlBuilder.hashCode();
    result = 31 * result + fromElementSqlBuilder.hashCode();
    result = 31 * result + conditionSqlBuilder.hashCode();
    result = 31 * result + expressionSqlBuilder.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultElementSqlBuilder{"
        + "selectSqlBuilder=" + selectSqlBuilder
        + ", selectClauseSqlBuilder=" + selectClauseSqlBuilder
        + ", selectColumnSqlBuilder=" + selectColumnSqlBuilder
        + ", fromClauseSqlBuilder=" + fromClauseSqlBuilder
        + ", fromElementSqlBuilder=" + fromElementSqlBuilder
        + ", conditionSqlBuilder=" + conditionSqlBuilder
        + ", expressionSqlBuilder=" + expressionSqlBuilder
        + '}';
  }
}
