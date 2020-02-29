package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.SqlTableColumn;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

class SqlColumnSimple extends SqlColumnBase implements SqlTableColumn {

  private final @Nullable SqlTableAlias tableAlias;
  private final SqlIdentifier column;

  SqlColumnSimple(@Nullable SqlTableAlias tableAlias, SqlIdentifier column,
      @Nullable SqlIdentifier alias) {
    super(alias);
    this.tableAlias = tableAlias;
    this.column = Objects.requireNonNull(column);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    if (tableAlias != null) {
      builder.append(tableAlias.getAlias()).append('.');
    }
    builder.append(column);
  }

  @Override
  public SqlTableColumn withAlias(SqlIdentifier alias) {
    if (getOptAlias().filter(al -> al.equals(alias)).isPresent()) {
      return this;
    }
    return new SqlColumnSimple(tableAlias, column, alias);
  }

  @Override
  public SqlTableColumn withTableAlias(SqlTableAlias newTableAlias) {
    if (newTableAlias.equals(this.tableAlias)) {
      return this;
    }
    return new SqlColumnSimple(newTableAlias, column, getAlias());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    SqlColumnSimple that = (SqlColumnSimple) o;
    return Objects.equals(tableAlias, that.tableAlias)
        && Objects.equals(column, that.column);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (tableAlias != null ? tableAlias.hashCode() : 0);
    result = 31 * result + (column != null ? column.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnSimple{"
        + "tableAlias=" + tableAlias
        + ", column=" + column
        + ", " + super.toString() + '}';
  }
}
