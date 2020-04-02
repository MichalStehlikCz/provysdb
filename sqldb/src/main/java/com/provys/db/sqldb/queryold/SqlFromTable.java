package com.provys.db.sqldb.queryold;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.common.exception.InternalException;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindVariable;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.FromElement;
import com.provys.db.query.NamePath;
import com.provys.db.query.SimpleName;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
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
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlFromElement
final class SqlFromTable implements SqlFromElement {

  @JsonProperty("TABLENAME")
  private final NamePath tableName;
  @JsonProperty("ALIAS")
  private final @Nullable SimpleName alias;
  private final Context<?, ?, ?, ?, ?, ?, ?> context;

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

  SqlFromTable(Context<?, ?, ?, ?, ?, ?, ?> context, NamePath tableName,
      @Nullable SimpleName alias) {
    this.context = context;
    validateTableName(tableName);
    this.tableName = tableName;
    this.alias = alias;
  }

  @JsonCreator
  SqlFromTable(@JsonProperty("TABLENAME") NamePath tableName,
      @JsonProperty("ALIAS") @Nullable SimpleName alias) {
    this(SqlContextImpl.getNoDbInstance(), tableName, alias);
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
  public <J extends FromElement> J transfer(Context<?, ?, ?, ?, J, ?, ?> targetContext,
      @Nullable BindMap bindMap) {
    return targetContext.from(tableName, alias);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public void append(CodeBuilder builder) {
    tableName.append(builder);
    if (alias != null) {
      builder.append(' ');
      alias.append(builder);
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFromTable that = (SqlFromTable) o;
    return tableName.equals(that.tableName)
        && Objects.equals(alias, that.alias)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = tableName.hashCode();
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    result = 31 * result + context.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlFromTable{"
        + "tableName=" + tableName
        + ", alias=" + alias
        + ", context=" + context
        + '}';
  }
}
