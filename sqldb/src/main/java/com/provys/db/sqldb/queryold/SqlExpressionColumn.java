package com.provys.db.sqldb.queryold;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindVariable;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.Expression;
import com.provys.db.query.FromContext;
import com.provys.db.query.NamePath;
import com.provys.db.query.SimpleName;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
import com.provys.db.sqldb.dbcontext.DefaultJsonClassDeserializer;
import com.provys.db.sqldb.dbcontext.DefaultJsonClassSerializer;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("COLUMN")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
final class SqlExpressionColumn implements SqlExpression {

  @JsonProperty("TABLE")
  private final @Nullable NamePath  table;
  @JsonProperty("COLUMN")
  private final SimpleName column;
  @JsonProperty("TYPE")
  @JsonSerialize(using = DefaultJsonClassSerializer.class)
  private final Class<?> type;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlExpressionColumn(SqlContext<?, ?, ?, ?, ?, ?, ?> context,
      @Nullable NamePath table, SimpleName column, Class<?> type,
      @Nullable FromContext fromContext) {
    this.context = context;
    if ((table != null) && (fromContext != null)) {
      var fromElement = fromContext.getFromElement(table);
      if (fromElement != null) {
        // we found from element - we want to verify column against that element and use its alias
        fromElement.validateColumn(column, type);
        this.table = fromContext.getDefaultAlias(fromElement);
      } else {
        // probably alias pointing to outside context, use as it was specified
        this.table = table;
      }
    } else {
      // no table or no context, use alias as originally specified
      this.table = table;
    }
    this.column = column;
    this.type = type;
  }

  @JsonCreator
  SqlExpressionColumn(@JsonProperty("TABLE") @Nullable NamePath table,
      @JsonProperty("COLUMN") SimpleName column,
      @JsonProperty("TYPE") @JsonDeserialize(using = DefaultJsonClassDeserializer.class)
          Class<?> type) {
    this(SqlContextImpl.getNoDbInstance(), table, column, type, null);
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
    if (table != null) {
      table.append(builder);
      builder.append('.');
    }
    column.append(builder);
  }

  @Override
  public Class<?> getType() {
    return type;
  }

  @Override
  public <E extends Expression> E transfer(Context<?, ?, ?, ?, ?, ?, E> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    if (targetContext.equals(context) && (fromContext == null) && (bindMap == null)) {
      @SuppressWarnings("unchecked")
      var result = (E) this;
      return result;
    }
    return targetContext.tableColumn(table, column, type, fromContext, bindMap);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlExpressionColumn that = (SqlExpressionColumn) o;
    return Objects.equals(table, that.table)
        && column.equals(that.column)
        && (type == that.type)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = table != null ? table.hashCode() : 0;
    result = 31 * result + column.hashCode();
    result = 31 * result + type.hashCode();
    result = 31 * result + context.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlExpressionColumn{"
        + "table=" + table
        + ", column=" + column
        + ", type=" + type
        + ", context=" + context
        + '}';
  }
}
