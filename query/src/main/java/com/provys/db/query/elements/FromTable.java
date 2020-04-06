package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Simple from clause based on table, without join (or potentially with join specified in where
 * clause which is valid on Oracle, less so on other databases).
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FROMTABLE")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from FromElement
final class FromTable implements FromElement {

  @JsonProperty("TABLENAME")
  private final NamePath tableName;
  @JsonProperty("ALIAS")
  private final @Nullable SimpleName alias;

  private static void validateTableName(NamePath tableName) {
    var segments = tableName.getDbNames();
    if (segments.isEmpty()) {
      throw new InternalException("Empty path is not valid database object name");
    }
    if (segments.size() > 2) {
      throw new InternalException(
          "Table name expected in form <scheme>.<name> or <name>, not " + tableName);
    }
  }

  /**
   * Create from element based on supplied table, with optional (but recommended) alias.
   *
   * @param tableName is name of table
   * @param alias is alias this source shall use in query
   */
  @JsonCreator
  FromTable(@JsonProperty("TABLENAME") NamePath tableName,
      @JsonProperty("ALIAS") @Nullable SimpleName alias) {
    validateTableName(tableName);
    this.tableName = tableName;
    this.alias = alias;
  }

  /**
   * Value of field tableName.
   *
   * @return value of field tableName
   */
  public NamePath getTableName() {
    return tableName;
  }

  @Override
  public @Nullable NamePath getAlias() {
    return (alias == null) ? tableName : alias;
  }

  @Override
  public void validateColumn(SimpleName column, Class<?> type) {
    // intentionally empty - cannot check columns
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
    consumer.fromTable(tableName, alias);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FromTable that = (FromTable) o;
    return tableName.equals(that.tableName)
        && Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = tableName.hashCode();
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FromTable{"
        + "tableName=" + tableName
        + ", alias=" + alias
        + '}';
  }
}
