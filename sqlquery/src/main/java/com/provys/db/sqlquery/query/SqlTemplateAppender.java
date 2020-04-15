package com.provys.db.sqlquery.query;

import com.google.errorprone.annotations.Immutable;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
final class SqlTemplateAppender implements SqlFunctionAppender {

  /**
   * Matcher to match arguments in template.
   */
  private static final Pattern ARGUMENT_PATTERN = Pattern.compile("(\\{[0-9]+})");

  private final String template;

  SqlTemplateAppender(String template) {
    this.template = template;
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
    var matcher = ARGUMENT_PATTERN.matcher(template);
    int pos = 0;
    while (matcher.find()) {
      builder.append(template.substring(pos, matcher.start()));
      var argIndex = Integer.parseInt(template.substring(matcher.start() + 1, matcher.end() - 1));
      argumentAppend.get(argIndex).accept(builder);
      pos = matcher.end();
    }
    builder.append(template.substring(pos));
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
    return Objects.equals(template, that.template);
  }

  @Override
  public int hashCode() {
    return template != null ? template.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlTemplateAppender{"
        + "template='" + template + '\''
        + '}';
  }
}
