package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.Select;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Select statement together with associated bind variables, as created from select builder.
 */
class SelectImpl implements Select {

  private final String sqlText;
  private final Collection<BindName> binds;

  SelectImpl(String sql) {
    this.sqlText = Objects.requireNonNull(sql);
    this.binds = Collections.emptyList();
  }

  SelectImpl(String sql, Collection<BindName> binds) {
    this.sqlText = Objects.requireNonNull(sql);
    this.binds = List.copyOf(binds);
  }

  @Override
  public String getSqlText() {
    return sqlText;
  }

  @Override
  public Collection<BindName> getBinds() {
    return binds;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SelectImpl select = (SelectImpl) o;
    return Objects.equals(sqlText, select.sqlText)
        && Objects.equals(binds, select.binds);
  }

  @Override
  public int hashCode() {
    int result = sqlText != null ? sqlText.hashCode() : 0;
    result = 31 * result + (binds != null ? binds.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SelectImpl{"
        + "sqlText='" + sqlText + '\''
        + ", binds=" + binds
        + '}';
  }
}
