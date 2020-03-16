package sqlbuilder.elements;

import com.provys.provysdb.sql.CodeBuilder;
import com.provys.provysdb.sql.SimpleName;
import sqlbuilder.QueryAlias;
import sqlbuilder.TableColumn;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlTableColumnAliasWrapper<T> implements TableColumn<T> {

  /**
   * Used to construct wrapper around column with specified alias
   *
   * @param column is source column that should be wrapped
   * @param alias is alias that will be used for column
   * @return column with same expression as column, but with specified alias
   * @param <U> is type of expression, represented by column
   */
  static <U> TableColumn<U> of(TableColumn<U> column, SimpleName alias) {
    if (column.getOptAlias().filter(oldAlias -> oldAlias.equals(alias)).isPresent()) {
      return column;
    }
    if (column instanceof SqlTableColumnAliasWrapper) {
      return new SqlTableColumnAliasWrapper<>(((SqlTableColumnAliasWrapper<U>) column).column, alias);
    }
    return new SqlTableColumnAliasWrapper<>(column, alias);
  }

  private final TableColumn<T> column;
  private final SimpleName alias;

  private SqlTableColumnAliasWrapper(TableColumn<T> column, SimpleName alias) {
    this.column = column;
    this.alias = alias;
  }

  @Override
  public SimpleName getAlias() {
    return alias;
  }

  @Override
  public TableColumn<T> as(SimpleName newAlias) {
    if (alias.equals(newAlias)) {
      return this;
    }
    return new SqlTableColumnAliasWrapper<>(column, alias);
  }

  @Override
  public TableColumn<T> withTableAlias(QueryAlias newTableAlias) {
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
