package com.provys.db.sqldb.sql;

import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromContext;
import com.provys.db.sql.NamePath;
import com.provys.db.sql.SimpleName;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlExpressionColumn implements SqlExpression {

  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;
  private final @Nullable NamePath  table;
  private final SimpleName column;
  private final Class<?> type;

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
    return context.equals(that.context)
        && Objects.equals(table, that.table)
        && column.equals(that.column)
        && (type == that.type);
  }

  @Override
  public int hashCode() {
    int result = context.hashCode();
    result = 31 * result + (table != null ? table.hashCode() : 0);
    result = 31 * result + column.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlExpressionColumn{"
        + "context=" + context
        + ", table=" + table
        + ", column=" + column
        + ", type=" + type
        + '}';
  }
}
