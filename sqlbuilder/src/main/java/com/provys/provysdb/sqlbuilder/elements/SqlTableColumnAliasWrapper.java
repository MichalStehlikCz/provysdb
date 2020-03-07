package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.SqlTableColumn;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlTableColumnAliasWrapper<T> implements SqlTableColumn<T> {

  /**
   * Used to construct wrapper around column with specified alias
   *
   * @param column is source column that should be wrapped
   * @param alias is alias that will be used for column
   * @return column with same expression as column, but with specified alias
   * @param <U> is type of expression, represented by column
   */
  static <U> SqlTableColumn<U> of(SqlTableColumn<U> column, SqlIdentifier alias) {
    if (column.getOptAlias().filter(oldAlias -> oldAlias.equals(alias)).isPresent()) {
      return column;
    }
    if (column instanceof SqlTableColumnAliasWrapper) {
      return new SqlTableColumnAliasWrapper<>(((SqlTableColumnAliasWrapper<U>) column).column, alias);
    }
    return new SqlTableColumnAliasWrapper<>(column, alias);
  }

  private final SqlTableColumn<T> column;
  private final SqlIdentifier alias;

  private SqlTableColumnAliasWrapper(SqlTableColumn<T> column, SqlIdentifier alias) {
    this.column = column;
    this.alias = alias;
  }

  @Override
  public SqlIdentifier getAlias() {
    return alias;
  }

  @Override
  public SqlTableColumn<T> as(SqlIdentifier newAlias) {
    if (alias.equals(newAlias)) {
      return this;
    }
    return new SqlTableColumnAliasWrapper<>(column, alias);
  }

  @Override
  public SqlTableColumn<T> withTableAlias(SqlTableAlias newTableAlias) {
    return of(column.withTableAlias(newTableAlias), alias);
  }

  @Override
  public Class<T> getType() {
    return column.getType();
  }

  @Override
  public void addSql(CodeBuilder builder) {
    column.addSql(builder);
    builder.append(' ').append(alias.getText());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlTableColumnAliasWrapper<?> that = (SqlTableColumnAliasWrapper<?>) o;
    return Objects.equals(column, that.column)
        && Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = column != null ? column.hashCode() : 0;
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlTableColumnAliasWrapper{"
        + "column=" + column
        + ", alias=" + alias
        + '}';
  }
}
