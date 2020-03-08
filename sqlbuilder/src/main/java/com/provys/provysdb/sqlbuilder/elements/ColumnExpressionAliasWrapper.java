package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SelectExpressionBuilder;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.Identifier;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ColumnExpressionAliasWrapper<T> implements SelectExpressionBuilder<T> {

  /**
   * Used to construct wrapper around column with specified alias.
   *
   * @param expression is source expression that should be wrapped
   * @param alias is alias that will be used for column
   * @return column with same expression as column, but with specified alias
   * @param <U> is type of expression, represented by column
   */
  static <U> SelectExpressionBuilder<U> of(Expression<U> expression, Identifier alias) {
    if (expression instanceof SelectExpressionBuilder) {
      var column = (SelectExpressionBuilder<U>) expression;
      if (column.getOptAlias().filter(oldAlias -> oldAlias.equals(alias)).isPresent()) {
        return column;
      }
      if (column instanceof ColumnExpressionAliasWrapper) {
        return new ColumnExpressionAliasWrapper<>(((ColumnExpressionAliasWrapper<U>) column).expression, alias);
      }
    }
    return new ColumnExpressionAliasWrapper<>(expression, alias);
  }

  /**
   * Used to construct wrapper around column with specified alias.
   *
   * @param expression is source expression that should be wrapped
   * @param alias is alias that will be used for column
   * @return column with same expression as column, but with specified alias
   * @param <U> is type of expression, represented by column
   */
  static <U> SelectExpressionBuilder<U> of(Expression<U> expression, String alias) {
    return of(expression, IdentifierImpl.parse(alias));
  }

  private final Expression<T> expression;
  private final Identifier alias;

  private ColumnExpressionAliasWrapper(Expression<T> expression, Identifier alias) {
    this.expression = expression;
    this.alias = alias;
  }

  @Override
  public Identifier getAlias() {
    return alias;
  }

  @Override
  public SelectExpressionBuilder<T> as(Identifier newAlias) {
    if (alias.equals(newAlias)) {
      return this;
    }
    return new ColumnExpressionAliasWrapper<>(expression, alias);
  }

  @Override
  public SelectExpressionBuilder<T> as(String newAlias) {
    return as(IdentifierImpl.parse(newAlias));
  }

  @Override
  public Class<T> getType() {
    return expression.getType();
  }

  @Override
  public void addColumn(CodeBuilder builder) {
    appendExpression(builder);
    builder.append(" ").append(alias.getText());
  }

  @Override
  public void appendExpression(CodeBuilder builder) {
    expression.appendExpression(builder);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnExpressionAliasWrapper<?> that = (ColumnExpressionAliasWrapper<?>) o;
    return Objects.equals(expression, that.expression)
        && Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = expression != null ? expression.hashCode() : 0;
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ColumnExpressionAliasWrapper{"
        + "expression=" + expression
        + ", alias=" + alias
        + '}';
  }
}
