package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ConditionSimple implements Condition {

  private final String sql;

  ConditionSimple(String sql) {
    this.sql = sql;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('(').append(sql).append(')');
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionSimple that = (ConditionSimple) o;
    return Objects.equals(sql, that.sql);
  }

  @Override
  public int hashCode() {
    return sql != null ? sql.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ConditionSimple{"
        + "sql='" + sql + '\''
        + '}';
  }
}
