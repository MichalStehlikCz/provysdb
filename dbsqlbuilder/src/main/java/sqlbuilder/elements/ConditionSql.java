package sqlbuilder.elements;

import com.provys.provysdb.sql.CodeBuilder;
import sqlbuilder.Condition;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ConditionSql implements Condition {

  private final String sql;

  ConditionSql(String sql) {
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
    ConditionSql that = (ConditionSql) o;
    return Objects.equals(sql, that.sql);
  }

  @Override
  public int hashCode() {
    return sql != null ? sql.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "ConditionSimple{"
        + "com.provys.db.sql='" + sql + '\''
        + '}';
  }
}
