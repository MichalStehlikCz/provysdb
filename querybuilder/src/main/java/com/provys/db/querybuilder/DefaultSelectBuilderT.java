package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class DefaultSelectBuilderT<T extends DefaultSelectBuilderT<T>> {

  private final List<FromElement> fromElements;
  private final List<Condition> conditions;
  private final ElementFactory elementFactory;

  DefaultSelectBuilderT(Collection<FromElement> fromElements, Collection<Condition> conditions,
      ElementFactory elementFactory) {
    this.fromElements = List.copyOf(fromElements);
    this.conditions = List.copyOf(conditions);
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
   * Value of field conditions.
   *
   * @return value of field conditions
   */
  protected List<Condition> getConditions() {
    return conditions;
  }

  /**
   * Value of field elementFactory.
   *
   * @return value of field elementFactory
   */
  protected ElementFactory getElementFactory() {
    return elementFactory;
  }

  public abstract T from(FromElement fromElement);

  public T fromTable(NamePath tableName, @Nullable SimpleName alias) {
    return from(elementFactory.fromTable(tableName, alias));
  }

  public T fromSelect(SelectT<?> select, @Nullable SimpleName alias) {
    return from(elementFactory.fromSelect(select, alias));
  }

  public T fromDual(@Nullable SimpleName alias) {
    return from(elementFactory.fromDual(alias));
  }

  public abstract T where(Condition condition);

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
        && conditions.equals(that.conditions)
        && elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    int result = fromElements.hashCode();
    result = 31 * result + conditions.hashCode();
    result = 31 * result + elementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSelectBuilderT{"
        + "fromElements=" + fromElements
        + ", conditions=" + conditions
        + ", elementFactory=" + elementFactory
        + '}';
  }
}
