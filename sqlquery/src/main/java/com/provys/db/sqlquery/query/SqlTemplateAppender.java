package com.provys.db.sqlquery.query;

import com.google.errorprone.annotations.Immutable;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
final class SqlTemplateAppender implements SqlBuiltInAppender {

  /**
   * Matcher to match arguments in template.
   */
  private static final Pattern ARGUMENT_PATTERN = Pattern.compile("(\\{[0-9]+})");

  private final String template;
  private final SqlBuilderPosition outerPriority;
  private final SqlBuilderPosition argumentPosition;

  SqlTemplateAppender(String template, SqlBuilderPosition outerPriority,
      SqlBuilderPosition argumentPosition) {
    this.template = template;
    this.outerPriority = outerPriority;
    this.argumentPosition = argumentPosition;
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
    if (outerPriority.compareTo(builder.getPosition()) > 0) {
      builder.append("(");
    }
    var matcher = ARGUMENT_PATTERN.matcher(template);
    builder.pushPosition(argumentPosition);
    int pos = 0;
    while (matcher.find()) {
      builder.append(template.substring(pos, matcher.start()));
      var argIndex = Integer.parseInt(template.substring(matcher.start() + 1, matcher.end() - 1));
      argumentAppend.get(argIndex).accept(builder);
      pos = matcher.end();
    }
    builder.append(template.substring(pos))
        .popPosition();
    if (outerPriority.compareTo(builder.getPosition()) > 0) {
      builder.append(")");
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
    SqlTemplateAppender that = (SqlTemplateAppender) o;
    return template.equals(that.template)
        && outerPriority == that.outerPriority
        && argumentPosition == that.argumentPosition;
  }

  @Override
  public int hashCode() {
    int result = template.hashCode();
    result = 31 * result + outerPriority.hashCode();
    result = 31 * result + argumentPosition.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlTemplateAppender{"
        + "template='" + template + '\''
        + ", outerPriority=" + outerPriority
        + ", argumentPosition=" + argumentPosition
        + '}';
  }
}
