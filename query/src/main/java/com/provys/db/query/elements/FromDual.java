package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents select without from clause - in Oracle, it is formally modelled as select from
 * pseudo-table dual, but other database engines might use different syntax
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FROMDUAL")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from FromElement
final class FromDual implements FromElement {

  private static final SimpleName DUAL = SimpleName.valueOf("dual");

  @JsonProperty("ALIAS")
  private final @Nullable SimpleName alias;

  @JsonCreator
  FromDual(@JsonProperty("ALIAS") @Nullable SimpleName alias) {
    this.alias = alias;
  }

  @Override
  public NamePath getAlias() {
    return (alias == null) ? DUAL : alias;
  }

  @Override
  public void validateColumn(SimpleName column, Class<?> type) {
    // no columns are possible...
    throw new NoSuchElementException("Column " + column + "cannot be selected from dual");
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public FromElement mapBinds(BindMap bindMap) {
    return this;
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.fromDual(alias);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FromDual fromDual = (FromDual) o;
    return Objects.equals(alias, fromDual.alias);
  }

  @Override
  public int hashCode() {
    return alias != null ? alias.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "FromDual{"
        + "alias=" + alias
        + '}';
  }
}
