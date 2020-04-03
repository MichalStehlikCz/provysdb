package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import com.provys.db.query.names.NamePath;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default implementation of from clause. Keeps list of from elements, exports them as comma
 * separated list, each from element on separate line. Implementation assumes that list is
 * relatively small, thus it is not necessary to index list by alias for faster access.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FROMCLAUSE")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlFromClause
@JsonSerialize(using = DefaultFromClauseSerializer.class)
@JsonDeserialize(using = DefaultFromClauseDeserializer.class)
public final class DefaultFromClause implements FromClause {

  private final List<FromElement> fromElements;

  /**
   * Create from clause, based on supplied elements.
   *
   * @param fromElements are elements from clause should be based on
   * @param bindMap can be used to map variables in from clause
   */
  public DefaultFromClause(Collection<? extends FromElement> fromElements,
      @Nullable BindMap bindMap) {
    this.fromElements = fromElements.stream()
        .map(fromElement -> (bindMap == null) ? fromElement : fromElement.mapBinds(bindMap))
        .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Simplified constructor, without bind translation.
   *
   * @param fromElements is collection of from elements in new clause
   */
  public DefaultFromClause(Collection<? extends FromElement> fromElements) {
    this(fromElements, null);
  }

  @Override
  public @Nullable FromElement getElementByAlias(NamePath alias) {
    FromElement result = null;
    for (var element : fromElements) {
      if (element.match(alias)) {
        if (result != null) {
          throw new InternalException(
              "Alias " + alias + "does not uniquely identify element in " + this + "; elements "
                  + result + " and " + element + " both match");
        }
        result = element;
      }
    }
    return result;
  }

  @Override
  public boolean isUnique(NamePath alias) {
    boolean found = false;
    for (var element : fromElements) {
      if (element.match(alias)) {
        if (found) {
          return false;
        }
        found = true;
      }
    }
    return true;
  }

  @Override
  public List<FromElement> getElements() {
    return Collections.unmodifiableList(fromElements);
  }

  @Override
  public Collection<BindVariable> getBinds() {
    var collector = new BindVariableCollector();
    for (var fromElement : fromElements) {
      collector.add(fromElement);
    }
    return collector.getBinds();
  }

  @Override
  public FromClause mapBinds(BindMap bindMap) {
    var newFromElements = fromElements.stream()
        .map(fromElement -> fromElement.mapBinds(bindMap))
        .collect(Collectors.toList());
    if (newFromElements.containsAll(fromElements)) {
      // one side comparison is sufficient, both lists have same number of items
      return this;
    }
    return new DefaultFromClause(newFromElements);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultFromClause that = (DefaultFromClause) o;
    return fromElements.equals(that.fromElements);
  }

  @Override
  public int hashCode() {
    return fromElements.hashCode();
  }

  @Override
  public String toString() {
    return "DefaultFromClause{"
        + "fromElements=" + fromElements
        + '}';
  }
}
