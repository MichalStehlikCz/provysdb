package com.provys.db.sqldb.sql;

import com.provys.common.exception.InternalException;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.FromElement;
import com.provys.db.sql.NamePath;
import com.provys.db.sql.SimpleName;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Simple from clause based on table, without join (or potentially with join specified in where
 * clause which is valid on Oracle, less so on other databases).
 */
final class SqlFromTable implements SqlFromElement {

  private final Context<?, ?, ?, ?, ?, ?, ?> context;
  private final NamePath tableName;
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

  SqlFromTable(Context<?, ?, ?, ?, ?, ?, ?> context, NamePath tableName,
      @Nullable SimpleName alias) {
    this.context = context;
    validateTableName(tableName);
    this.tableName = tableName;
    this.alias = alias;
  }

  @Override
  public @Nullable NamePath getAlias() {
    return (alias == null) ? tableName : alias;
  }

  @Override
  public <J extends FromElement> J transfer(Context<?, ?, ?, ?, J, ?, ?> targetContext,
      @Nullable BindMap bindMap) {
    if (context.equals(targetContext)) {
      @SuppressWarnings("unchecked")
      var result = (J) this;
      return result;
    }
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
    return Objects.equals(context, that.context)
        && Objects.equals(tableName, that.tableName)
        && Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = context != null ? context.hashCode() : 0;
    result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlFromTable{"
        + "context=" + context
        + ", tableName=" + tableName
        + ", alias=" + alias
        + '}';
  }
}
