package sqlbuilder.elements;

import com.provys.provysdb.sql.BindName;
import com.provys.provysdb.sql.CodeBuilder;
import sqlbuilder.Condition;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ConditionSimpleWithBinds implements Condition {

  private final String sql;
  private final List<BindName> binds;

  ConditionSimpleWithBinds(String sql, Collection<? extends BindName> binds) {
    this.sql = sql;
    this.binds = List.copyOf(binds);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('(').append(sql).append(')');
    builder.addBinds(binds);
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
    ConditionSimpleWithBinds that = (ConditionSimpleWithBinds) o;
    return Objects.equals(sql, that.sql)
        && Objects.equals(binds, that.binds);
  }

  @Override
  public int hashCode() {
    int result = sql != null ? sql.hashCode() : 0;
    result = 31 * result + (binds != null ? binds.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ConditionSimpleWithBinds{"
        + "com.provys.db.sql='" + sql + '\''
        + ", binds=" + binds
        + '}';
  }
}
