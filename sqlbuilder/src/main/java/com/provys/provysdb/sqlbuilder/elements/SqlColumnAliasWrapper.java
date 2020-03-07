package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnAliasWrapper<T> implements SqlColumn<T> {

  /**
   * Used to construct wrapper around column with specified alias
   *
   * @param column is source column that should be wrapped
   * @param alias is alias that will be used for column
   * @return column with same expression as column, but with specified alias
   * @param <U> is type of expression, represented by column
   */
  static <U> SqlColumn<U> of(SqlColumn<U> column, SqlIdentifier alias) {
    if (column.getOptAlias().filter(oldAlias -> oldAlias.equals(alias)).isPresent()) {
      return column;
    }
    if (column instanceof SqlColumnAliasWrapper) {
      return new SqlColumnAliasWrapper<>(((SqlColumnAliasWrapper<U>) column).column, alias);
    }
    return new SqlColumnAliasWrapper<>(column, alias);
  }

  private final SqlColumn<T> column;
  private final SqlIdentifier alias;

  private SqlColumnAliasWrapper(SqlColumn<T> column, SqlIdentifier alias) {
    this.column = column;
    this.alias = alias;
  }

  @Override
  public SqlIdentifier getAlias() {
    return alias;
  }

  @Override
  public SqlColumn<T> as(SqlIdentifier newAlias) {
    if (alias.equals(newAlias)) {
      return this;
    }
    return new SqlColumnAliasWrapper<>(column, alias);
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
    SqlColumnAliasWrapper<?> that = (SqlColumnAliasWrapper<?>) o;
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
    return "SqlColumnAliasWrapper{"
        + "column=" + column
        + ", alias=" + alias
        + '}';
  }
}
