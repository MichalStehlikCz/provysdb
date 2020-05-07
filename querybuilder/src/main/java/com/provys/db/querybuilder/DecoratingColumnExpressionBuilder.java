package com.provys.db.querybuilder;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.SelectColumn;
import com.provys.db.query.functions.ConditionalOperator;
import com.provys.db.query.names.SimpleName;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
class DecoratingColumnExpressionBuilder<T> implements ExpressionBuilder<T> {

  private final Expression<T> expression;
  private final @Nullable SimpleName defaultAlias;
  private final ElementFactory elementFactory;

  DecoratingColumnExpressionBuilder(Expression<T> expression, @Nullable SimpleName defaultAlias,
      ElementFactory elementFactory) {
    this.expression = expression;
    this.defaultAlias = defaultAlias;
    this.elementFactory = elementFactory;
  }

  @Override
  public Class<T> getType() {
    return expression.getType();
  }

  @Override
  public void appendBinds(BindVariableCombiner combiner) {
    combiner.addElement(expression);
  }

  @Override
  public Expression<T> build() {
    return expression;
  }

  @Override
  public SelectColumn<T> buildColumn() {
    return elementFactory.selectColumn(expression, defaultAlias);
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

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DecoratingColumnExpressionBuilder<?> that = (DecoratingColumnExpressionBuilder<?>) o;
    return expression.equals(that.expression)
        && Objects.equals(defaultAlias, that.defaultAlias)
        && elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    int result = expression.hashCode();
    result = 31 * result + (defaultAlias != null ? defaultAlias.hashCode() : 0);
    result = 31 * result + elementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DecoratingColumnExpressionBuilder{"
        + "expression=" + expression
        + ", defaultAlias=" + defaultAlias
        + ", elementFactory=" + elementFactory
        + '}';
  }
}
