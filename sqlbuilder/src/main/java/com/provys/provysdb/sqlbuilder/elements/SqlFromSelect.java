package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.QueryAlias;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlFromSelect extends SqlFromBase {

  private final Select select;

  SqlFromSelect(Select select, QueryAlias alias) {
    super(alias);
    this.select = Objects.requireNonNull(select);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('(').appendLine()
        .increasedIdent(2)
        .appendWrapped(select.getSqlText())
        .popIdent()
        .append(") ")
        .append(getAlias());
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
    SqlFromSelect that = (SqlFromSelect) o;
    return Objects.equals(select, that.select);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (select != null ? select.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlFromSelect{"
        + "select=" + select
        + ", " + super.toString() + '}';
  }
}
