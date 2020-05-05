package com.provys.db.sqlquery.query;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import java.util.List;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Appender used for AND or OR connected conditions.
 */
@Immutable
final class SqlCondOpAppender implements SqlBuiltInAppender {

  private final String operator;
  private final SqlBuilderPosition priority;

  SqlCondOpAppender(String operator, SqlBuilderPosition priority) {
    if (operator.length() > 3) {
      throw new InternalException("Conditional operator " + operator + " too long");
    }
    this.operator = String.format("%-4s", operator);
    this.priority = priority;
  }

  /**
   * Append function this appender belongs to builder.
   *
   * @param argumentAppend is procedure that appends given argument to builder
   * @param builder        is builder where whole expression should be appended
   */
  @Override
  public <B extends SqlBuilder<B>> void append(List<? extends Consumer<? super B>> argumentAppend,
      B builder) {
    if ((priority.compareTo(builder.getPosition()) > 0)
        || ((priority == SqlBuilderPosition.COND_AND)
        && (builder.getPosition() == SqlBuilderPosition.COND_OR))) {
      builder.appendLine("(")
          .increasedIdent(2);
    }
    builder.pushPosition(priority);
    boolean first = true;
    for (var argument : argumentAppend) {
      if (first) {
        first = false;
        if (builder.getPosition() != SqlBuilderPosition.WHERE) {
          // append on start of where clause is handled by where clause itself
          builder.append("    ");
        }
      } else {
        builder.appendLine()
            .append(operator);
      }
      argument.accept(builder);
    }
    builder.popPosition();
    if (priority.compareTo(builder.getPosition()) > 0) {
      builder.popIdent()
          .appendLine()
          .append("    )");
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlCondOpAppender that = (SqlCondOpAppender) o;
    return operator.equals(that.operator)
        && priority == that.priority;
  }

  @Override
  public int hashCode() {
    int result = operator.hashCode();
    result = 31 * result + priority.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlCondOpAppender{"
        + "operator='" + operator + '\''
        + ", priority=" + priority
        + '}';
  }
}
