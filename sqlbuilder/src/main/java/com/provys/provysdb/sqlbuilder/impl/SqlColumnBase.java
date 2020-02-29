package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SqlColumnBase implements SqlColumn {

  private final @Nullable SqlIdentifier alias;

  SqlColumnBase(@Nullable SqlIdentifier alias) {
    this.alias = alias;
  }

  @Override
  public @Nullable SqlIdentifier getAlias() {
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
    SqlColumnBase that = (SqlColumnBase) o;
    return Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    return alias != null ? alias.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlColumnBase{"
        + "alias=" + alias
        + '}';
  }
}
