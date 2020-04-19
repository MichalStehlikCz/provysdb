package com.provys.db.querybuilder;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
final class DecoratingConditionBuilder implements StartConditionBuilder {

  private final Condition condition;
  private final ElementFactory elementFactory;

  DecoratingConditionBuilder(Condition condition, ElementFactory elementFactory) {
    this.condition = condition;
    this.elementFactory = elementFactory;
  }

  @Override
  public void appendBinds(BindVariableCombiner combiner) {
    combiner.addElement(condition);
  }

  @Override
  public AndConditionBuilder and(@Nullable Condition newCondition) {
    return new CombiningConditionBuilderAnd(elementFactory)
        .and(condition)
        .and(newCondition);
  }

  @Override
  public AndConditionBuilder and(Collection<? extends Condition> newConditions) {
    return new CombiningConditionBuilderAnd(elementFactory)
        .and(condition)
        .and(newConditions);
  }

  @Override
  public AndConditionBuilder and(ConditionBuilder newCondition) {
    return new CombiningConditionBuilderAnd(elementFactory)
        .and(condition)
        .and(newCondition);
  }

  @Override
  public OrConditionBuilder or(Condition newCondition) {
    return new CombiningConditionBuilderOr(elementFactory)
        .or(condition)
        .or(newCondition);
  }

  @Override
  public OrConditionBuilder or(Collection<? extends Condition> newConditions) {
    return new CombiningConditionBuilderOr(elementFactory)
        .or(condition)
        .or(newConditions);
  }

  @Override
  public OrConditionBuilder or(ConditionBuilder newCondition) {
    return new CombiningConditionBuilderOr(elementFactory)
        .or(condition)
        .or(newCondition);
  }

  @Override
  public Condition build() {
    return condition;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DecoratingConditionBuilder that = (DecoratingConditionBuilder) o;
    return condition.equals(that.condition)
        && elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    int result = condition.hashCode();
    result = 31 * result + elementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DecoratingConditionBuilder{"
        + "condition=" + condition
        + ", elementFactory=" + elementFactory
        + '}';
  }
}
