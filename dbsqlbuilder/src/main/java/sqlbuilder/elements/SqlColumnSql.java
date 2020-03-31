package sqlbuilder.elements;

import com.provys.provysdb.sql.BindName;
import com.provys.provysdb.sql.CodeBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnSql<T> extends ColumnExpressionBaseWithType<T> {

  private final String sql;
  private final List<BindName> binds;

  SqlColumnSql(String sql, Class<T> type) {
    super(type);
    this.sql = Objects.requireNonNull(sql);
    this.binds = Collections.emptyList();
  }

  SqlColumnSql(String sql, Class<T> type, Collection<? extends BindName> binds) {
    super(type);
    this.sql = Objects.requireNonNull(sql);
    this.binds = List.copyOf(binds);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.appendWrapped(sql, 4);
    builder.addBinds(binds);
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
    SqlColumnSql<?> that = (SqlColumnSql<?>) o;
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
        + "com.provys.db.sql='" + sql + '\''
        + ", binds=" + binds
        + ", " + super.toString() + '}';
  }
}
