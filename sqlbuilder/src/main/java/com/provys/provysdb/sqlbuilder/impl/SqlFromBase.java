package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SqlFromBase implements SqlFrom {

  private final SqlTableAlias alias;

  SqlFromBase(SqlTableAlias alias) {
    this.alias = Objects.requireNonNull(alias);
  }

  @Override
  public SqlTableAlias getAlias() {
    return alias;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFromBase that = (SqlFromBase) o;
    return Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    return alias != null ? alias.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlFromBase{"
        + "alias=" + alias
        + '}';
  }
}
