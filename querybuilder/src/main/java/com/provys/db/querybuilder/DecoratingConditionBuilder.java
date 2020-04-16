package com.provys.db.querybuilder;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
final class DecoratingConditionBuilder implements ConditionBuilder {

  private final Condition condition;
  private final ElementFactory elementFactory;

  DecoratingConditionBuilder(Condition condition, ElementFactory elementFactory) {
    this.condition = condition;
    this.elementFactory = elementFactory;
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
