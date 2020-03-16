package sqlbuilder.elements;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sql.CodeBuilder;
import sqlbuilder.Condition;
import com.provys.provysdb.sql.Expression;
import sqlparser.SqlSymbol;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ConditionCompare<T> implements Condition {

  private final Expression<T> first;
  private final Expression<T> second;
  private final SqlSymbol comparison;

  ConditionCompare(Expression<T> first, Expression<T> second, SqlSymbol comparison) {
    this.first = first;
    this.second = second;
    if (!comparison.isComparison()) {
      throw new InternalException(
          "Invalid comparison - symbol " + comparison.getSymbol() + "not valid");
    }
    this.comparison = comparison;
  }

  @Override
  public boolean isEmpty() {
    return (comparison == SqlSymbol.EQUAL) && first.equals(second);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('(');
    first.addSql(builder);
    builder.append(comparison.getSymbol());
    second.addSql(builder);
    builder.append(')');
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionCompare<?> that = (ConditionCompare<?>) o;
    return Objects.equals(first, that.first)
        && Objects.equals(second, that.second)
        && comparison == that.comparison;
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (second != null ? second.hashCode() : 0);
    result = 31 * result + (comparison != null ? comparison.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ConditionCompare{"
        + "first=" + first
        + ", second=" + second
        + ", comparison=" + comparison
        + '}';
  }
}
