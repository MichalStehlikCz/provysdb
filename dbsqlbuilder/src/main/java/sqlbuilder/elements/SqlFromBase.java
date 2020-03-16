package sqlbuilder.elements;

import sqlbuilder.FromClause;
import sqlbuilder.QueryAlias;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SqlFromBase implements FromClause {

  private final QueryAlias alias;

  SqlFromBase(QueryAlias alias) {
    this.alias = Objects.requireNonNull(alias);
  }

  @Override
  public QueryAlias getAlias() {
    return alias;
  }

  @SuppressWarnings("EqualsGetClass")
  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFromBase that = (SqlFromBase) o;
    return Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    return alias != null ? alias.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlFromBase{"
        + "alias=" + alias
        + '}';
  }
}
