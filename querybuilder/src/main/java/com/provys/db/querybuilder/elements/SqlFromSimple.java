package com.provys.db.querybuilder.elements;

import com.provys.provysdb.sql.CodeBuilder;
import com.provys.provysdb.sql.SimpleName;
import com.provys.db.querybuilder.QueryAlias;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used to represent element in from clause, built on table or view.
 */
final class SqlFromSimple extends SqlFromBase {

  private final SimpleName tableName;

  SqlFromSimple(SimpleName tableName, QueryAlias alias) {
    super(alias);
    this.tableName = Objects.requireNonNull(tableName);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append(tableName).append(' ').append(getAlias());
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
    SqlFromSimple that = (SqlFromSimple) o;
    return Objects.equals(tableName, that.tableName);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlFromSimple{"
        + "tableName=" + tableName
        + ", " + super.toString() + '}';
  }
}
