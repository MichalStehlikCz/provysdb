package sqlbuilder.elements;

import com.provys.provysdb.sql.CodeBuilder;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import com.provys.provysdb.sql.SimpleName;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnTypeWrapper<T> extends ColumnExpressionBase<T> {

  static <U> SqlColumn<U> of(SqlColumn<?> column, Class<U> type) {
    if (type.isAssignableFrom(column.getType())) {
      @SuppressWarnings({"unchecked", "java:S1854"}) // safe based on test, not useless
      var result = (SqlColumn<U>) column;
      return result;
    }
    return new SqlColumnTypeWrapper<>(column, type);
  }

  private final SqlColumn<?> column;
  private final Class<T> type;

  SqlColumnTypeWrapper(SqlColumn<?> column, Class<T> type) {
    this.column = column;
    this.type = type;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public @Nullable SimpleName getAlias() {
    return column.getAlias();
  }

  @Override
  public void addSql(CodeBuilder builder) {
    column.addSql(builder);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlColumnTypeWrapper<?> that = (SqlColumnTypeWrapper<?>) o;
    return Objects.equals(column, that.column)
        && (type == that.type);
  }

  @Override
  public int hashCode() {
    int result = column != null ? column.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnTypeWrapper{"
        + "column=" + column
        + ", type=" + type
        + ", " + super.toString() + '}';
  }
}
