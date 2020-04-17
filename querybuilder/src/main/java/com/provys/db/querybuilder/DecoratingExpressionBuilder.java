package com.provys.db.querybuilder;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.SimpleName;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
class DecoratingExpressionBuilder<T> implements ExpressionBuilder<T> {

  private final Expression<T> expression;
  private final ElementFactory elementFactory;

  DecoratingExpressionBuilder(Expression<T> expression, ElementFactory elementFactory) {
    this.expression = expression;
    this.elementFactory = elementFactory;
  }

  /**
   * Get type of expression being built.
   *
   * @return type of expression being built
   */
  @Override
  public Class<T> getType() {
    return expression.getType();
  }

  @Override
  public Expression<T> build() {
    return expression;
  }

  @Override
  public SelectColumn<T> buildColumn() {
    return elementFactory.selectColumn(expression, null);
  }

  @Override
  public SelectColumn<T> as(SimpleName alias) {
    return elementFactory.selectColumn(expression, alias);
  }

  @Override
  public ConditionBuilder eq(Expression<T> compareWith) {
    return new DecoratingConditionBuilder(elementFactory.condition(
        ConditionalOperator.COND_EQ_NONNULL, List.of(expression, compareWith)),
        elementFactory);
  }

  @Override
  public ConditionBuilder eqNullable(Expression<T> compareWith) {
    return new DecoratingConditionBuilder(elementFactory.condition(
        ConditionalOperator.COND_EQ_NULLABLE, List.of(expression, compareWith)),
        elementFactory);
  }

  @SuppressWarnings("EqualsGetClass")
  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DecoratingExpressionBuilder<?> that = (DecoratingExpressionBuilder<?>) o;
    return expression.equals(that.expression)
        && elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    int result = expression.hashCode();
    result = 31 * result + elementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DecoratingExpressionBuilder{"
        + "expression=" + expression
        + ", elementFactory=" + elementFactory
        + '}';
  }
}
