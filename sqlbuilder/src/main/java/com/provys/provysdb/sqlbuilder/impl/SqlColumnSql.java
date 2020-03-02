package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnSql extends SqlColumnBase {

  private final String sql;
  private final List<BindName> binds;

  SqlColumnSql(String sql, @Nullable SqlIdentifier alias) {
    super(alias);
    this.sql = Objects.requireNonNull(sql);
    this.binds = Collections.emptyList();
  }

  SqlColumnSql(String sql, @Nullable SqlIdentifier alias, Collection<? extends BindName> binds) {
    super(alias);
    this.sql = Objects.requireNonNull(sql);
    this.binds = List.copyOf(binds);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.appendWrapped(sql, 4);
    builder.addBinds(binds);
  }

  @Override
  public SqlColumn withAlias(SqlIdentifier alias) {
    if (getOptAlias()
        .filter(al -> al.equals(alias))
        .isPresent()) {
      return this;
    }
    return new SqlColumnSql(sql, alias);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SqlColumnSql)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    SqlColumnSql that = (SqlColumnSql) o;
    return Objects.equals(sql, that.sql)
        && Objects.equals(binds, that.binds);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (sql != null ? sql.hashCode() : 0);
    result = 31 * result + (binds != null ? binds.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnSql{"
        + "sql='" + sql + '\''
        + ", binds=" + binds
        + ", " + super.toString() + '}';
  }
}
