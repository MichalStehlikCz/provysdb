package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.SqlTableColumn;
import com.provys.provysdb.sqlbuilder.SqlTableColumnT;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

class SqlTableColumnTImpl<T> implements SqlTableColumnT<T> {

  private final SqlTableColumn column;
  private final Class<T> type;

  SqlTableColumnTImpl(SqlTableColumn column, Class<T> type) {
    this.column = Objects.requireNonNull(column);
    this.type = Objects.requireNonNull(type);
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public @Nullable SqlIdentifier getAlias() {
    return column.getAlias();
  }

  @Override
  public void addSql(CodeBuilder builder) {
    column.addSql(builder);
  }

  @Override
  public SqlTableColumnT<T> withAlias(SqlIdentifier alias) {
    var baseColumn = column.withAlias(alias);
    if (baseColumn == column) {
      return this;
    }
    return new SqlTableColumnTImpl<>(baseColumn, type);
  }

  @Override
  public SqlTableColumnT<T> withTableAlias(SqlTableAlias tableAlias) {
    var baseColumn = column.withTableAlias(tableAlias);
    if (baseColumn == column) {
      return this;
    }
    return new SqlTableColumnTImpl<>(baseColumn, type);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlTableColumnTImpl<?> that = (SqlTableColumnTImpl<?>) o;
    return Objects.equals(column, that.column)
        && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    int result = column != null ? column.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlTableColumnTImpl{"
        + "column=" + column
        + ", type=" + type
        + '}';
  }
}
