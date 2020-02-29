package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

class SqlColumnTImpl<T> implements SqlColumnT<T> {

  private final SqlColumn column;
  private final Class<T> type;

  SqlColumnTImpl(SqlColumn column, Class<T> type) {
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
  public SqlColumnT<T> withAlias(SqlIdentifier alias) {
    if (alias.equals(getAlias())) {
      return this;
    }
    return new SqlColumnTImpl<>(column.withAlias(alias), type);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlColumnTImpl<?> that = (SqlColumnTImpl<?>) o;
    return Objects.equals(column, that.column)
        && (type == that.type);
  }

  @Override
  public int hashCode() {
    int result = column != null ? column.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnTImpl{"
        + "column=" + column
        + ", type=" + type
        + '}';
  }
}
