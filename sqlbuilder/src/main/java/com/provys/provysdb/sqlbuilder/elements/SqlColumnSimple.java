package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.SqlTableColumn;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnSimple<T> extends SqlColumnType<T> implements SqlTableColumn<T> {

  /**
   * Creates simple column without type specification.
   *
   * @param tableAlias is table alias (optional)
   * @param column is name of column
   * @return created column with required table alias and column name
   */
  static SqlColumnSimple<Object> of(@Nullable SqlTableAlias tableAlias, SqlIdentifier column) {
    return new SqlColumnSimple<>(tableAlias, column, Object.class);
  }

  /**
   * Creates simple column without type specification.
   *
   * @param tableAlias is table alias (optional)
   * @param column is name of column
   * @param type is type of column value
   * @return created column with required table alias and column name
   * @param <U> is type parameter representing type of created column value
   */
  static <U> SqlColumnSimple<U> of(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, Class<U> type) {
    return new SqlColumnSimple<>(tableAlias, column, type);
  }

  private final @Nullable SqlTableAlias tableAlias;
  private final SqlIdentifier column;

  private SqlColumnSimple(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, Class<T> type) {
    super(type);
    this.tableAlias = tableAlias;
    this.column = column;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    if (tableAlias != null) {
      builder.append(tableAlias.getAlias()).append('.');
    }
    builder.append(column);
  }

  @Override
  public SqlTableColumn<T> withTableAlias(SqlTableAlias newTableAlias) {
    if (newTableAlias.equals(this.tableAlias)) {
      return this;
    }
    return new SqlColumnSimple<>(newTableAlias, column, getType());
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
    SqlColumnSimple<?> that = (SqlColumnSimple<?>) o;
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
