package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromClause;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Mutable builder for select statement. Adding from elements and conditions modifies internal
 * fields of this builder.
 *
 * @param <T> is type of builder, allows to return self reference of correct type
 */
abstract class DefaultSelectBuilderT<T extends DefaultSelectBuilderT<T>> {

  private final List<FromElement> fromElements;
  private final AndConditionBuilder conditionBuilder;
  private final ElementFactory elementFactory;

  DefaultSelectBuilderT(ElementFactory elementFactory) {
    this.fromElements = new ArrayList<>(5);
    this.conditionBuilder = new CombiningConditionBuilderAnd(elementFactory);
    this.elementFactory = elementFactory;
  }

  DefaultSelectBuilderT(Collection<FromElement> fromElements, @Nullable Condition condition,
      ElementFactory elementFactory) {
    this.fromElements = new ArrayList<>(fromElements);
    this.conditionBuilder = new CombiningConditionBuilderAnd(elementFactory)
        .and(condition);
    this.elementFactory = elementFactory;
  }

  /**
   * Value of field fromElements.
   *
   * @return value of field fromElements
   */
  protected List<FromElement> getFromElements() {
    return fromElements;
  }

  /**
   * Build from clause from from elements.
   *
   * @return from clause built from from elements
   */
  protected FromClause getFromClause() {
    return elementFactory.from(Collections.unmodifiableList(fromElements));
  }

  /**
   * Value of field conditionBuilder.
   *
   * @return value of field conditionBuilder
   */
  protected AndConditionBuilder getConditionBuilder() {
    return conditionBuilder;
  }

  /**
   * Value of field elementFactory.
   *
   * @return value of field elementFactory
   */
  protected ElementFactory getElementFactory() {
    return elementFactory;
  }

  /**
   * Append binds to combiner.
   *
   * @param combiner is bind variable collector that combines (merges) variables
   */
  protected void appendBinds(BindVariableCombiner combiner) {
    combiner.addElements(fromElements);
    combiner.addBuilder(conditionBuilder);
  }

  /**
   * Build and return condition.
   *
   * @return condition built from builder
   */
  protected @Nullable Condition getCondition() {
    return conditionBuilder.build();
  }

  protected abstract T self();

  public T from(FromElement fromElement) {
    fromElements.add(fromElement);
    return self();
  }

  public T fromTable(NamePath tableName, @Nullable SimpleName alias) {
    return from(elementFactory.fromTable(tableName, alias));
  }

  public T fromSelect(SelectT<?> select, @Nullable SimpleName alias) {
    return from(elementFactory.fromSelect(select, alias));
  }

  public T fromDual(@Nullable SimpleName alias) {
    return from(elementFactory.fromDual(alias));
  }

  public T where(Condition newCondition) {
    conditionBuilder.and(newCondition);
    return self();
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
    DefaultSelectBuilderT<?> that = (DefaultSelectBuilderT<?>) o;
    return fromElements.equals(that.fromElements)
        && conditionBuilder.equals(that.conditionBuilder)
        && elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    int result = fromElements.hashCode();
    result = 31 * result + conditionBuilder.hashCode();
    result = 31 * result + elementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSelectBuilderT{"
        + "fromElements=" + fromElements
        + ", conditionBuilder=" + conditionBuilder
        + ", elementFactory=" + elementFactory
        + '}';
  }
}
