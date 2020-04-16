package com.provys.db.sqlquery.query;

import com.google.errorprone.annotations.Immutable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Decorator that support unnesting repeated second argument, as used in functions, defined by
 * RepeatingBuiltInBase. In case number of arguments is two, they are directly passed to decorated
 * appender. If number of arguments is bigger, recursive calls are generated using supplied
 * appender.
 */
@Immutable
final class SqlRecursiveAppender implements SqlBuiltInAppender {

  /**
   * Create recursive appender, decorating template appender based on supplied template.
   *
   * @param template is template used for decorated template appender
   * @param outerPriority is outer priority of templated statement, used to decide if it should be
   *                     surrounded by brackets
   * @param argumentPosition is position that should be used as context when building arguments
   * @return recursive appender for processing given template
   */
  static SqlRecursiveAppender forTemplate(String template, SqlBuilderPosition outerPriority,
      SqlBuilderPosition argumentPosition) {
    return new SqlRecursiveAppender(
        new SqlTemplateAppender(template, outerPriority, argumentPosition));
  }

  private final SqlBuiltInAppender baseAppender;

  SqlRecursiveAppender(SqlBuiltInAppender baseAppender) {
    this.baseAppender = baseAppender;
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
    if (argumentAppend.size() > 2) {
      // in case of repeatable last argument, we evaluate template repeatedly
      // we create list with all but first argument, that will be used to append second argument
      var afterFirst = argumentAppend.subList(1, argumentAppend.size());
      baseAppender
          .append(List.of(argumentAppend.get(0), (Consumer<B>) bld -> append(afterFirst, bld)),
              builder);
    } else {
      baseAppender.append(argumentAppend, builder);
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
    SqlRecursiveAppender that = (SqlRecursiveAppender) o;
    return Objects.equals(baseAppender, that.baseAppender);
  }

  @Override
  public int hashCode() {
    return baseAppender != null ? baseAppender.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlRecursiveAppender{"
        + "baseAppender=" + baseAppender
        + '}';
  }
}
