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
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used for from element, representing inner select statement.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FROMSELECT")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from FromElement
final class FromSelect implements FromElement {

  @JsonProperty("SELECT")
  private final SelectT<?> select;
  @JsonProperty("ALIAS")
  private final @Nullable SimpleName alias;

  FromSelect(SelectT<?> select, @Nullable SimpleName alias, @Nullable BindMap bindMap) {
    this.select = (bindMap == null) ? select : select.mapBinds(bindMap);
    this.alias = alias;
  }

  @JsonCreator
  FromSelect(@JsonProperty("SELECT") SelectT<?> select,
      @JsonProperty("ALIAS") @Nullable SimpleName alias) {
    this(select, alias, null);
  }

  @Override
  public @Nullable NamePath getAlias() {
    return alias;
  }

  @Override
  public void validateColumn(SimpleName column, Class<?> type) {
    // support not implemented in select
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return select.getBinds();
  }

  @Override
  public FromElement mapBinds(BindMap bindMap) {
    var newSelect = select.mapBinds(bindMap);
    if (newSelect.equals(select)) {
      return this;
    }
    return new FromSelect(select, alias);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.fromSelect(select, alias);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FromSelect that = (FromSelect) o;
    return select.equals(that.select)
        && Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = select.hashCode();
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FromSelect{"
        + "select=" + select
        + ", alias=" + alias
        + '}';
  }
}
