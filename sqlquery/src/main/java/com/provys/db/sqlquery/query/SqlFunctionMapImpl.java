package com.provys.db.sqlquery.query;

import static com.provys.db.query.elements.Function.ANY_NVL;
import static com.provys.db.query.elements.Function.DATE_SYSDATE;
import static com.provys.db.query.elements.Function.STRING_CHR;
import static com.provys.db.query.elements.Function.STRING_CONCAT;

import com.provys.db.query.elements.Function;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SqlFunctionMapImpl implements SqlFunctionMap {

  /**
   * Matcher to match arguments in template.
   */
  private static final Pattern ARGUMENT_PATTERN = Pattern.compile("(\\{[0-9]+})");

  private static final SqlFunctionMapImpl DEFAULT = new SqlFunctionMapImpl(Map.of(
      STRING_CHR, "CHR({0})",
      STRING_CONCAT, "{0}||{1}",
      DATE_SYSDATE, "SYSDATE",
      ANY_NVL, "NVL({0}, {1})"
  ));

  public static SqlFunctionMapImpl getDefault() {
    return DEFAULT;
  }

  private final Map<Function, String> templateByFunction;

  public SqlFunctionMapImpl(Map<Function, String> templateByFunction) {
    this.templateByFunction = Map.copyOf(templateByFunction);
  }

  @Override
  public Map<Function, String> getTemplateByFunction() {
    return templateByFunction;
  }

  @Override
  public String getTemplate(Function function) {
    var result = templateByFunction.get(function);
    if (result == null) {
      throw new NoSuchElementException("Template not found for function " + function);
    }
    return result;
  }

  private static final class SqlBuilderAppender implements Consumer<CodeBuilder> {

    private final CodeBuilder appendBuilder;

    SqlBuilderAppender(CodeBuilder appendBuilder) {
      this.appendBuilder = appendBuilder;
    }

    @Override
    public void accept(CodeBuilder builder) {
      builder.append(appendBuilder);
    }
  }

  @Override
  public <B extends SqlBuilder<B>> void append(Function function,
      List<? extends Consumer<? super B>> argumentAppend, B builder) {
    // in case of repeatable last argument, we evaluate template repeatedly
    if (function.lastArgumentRepeatable() && (argumentAppend.size() > 2)) {
      // first, we evaluate result for first two arguments
      B recursiveBuilder = builder.getClone();
      List<? extends Consumer<? super B>> firstTwo = List.of(argumentAppend.get(0), argumentAppend.get(1));
      append(function, firstTwo, recursiveBuilder);
      // and now we recursively apply next argument
      //noinspection ForLoopReplaceableByWhile
      for (int i = 2; i < argumentAppend.size(); i++) {
        var nextBuilder = builder.getClone();
        List<? extends Consumer<? super B>> nextTwo = List.of(new SqlBuilderAppender(recursiveBuilder), argumentAppend.get(i));
        append(function, nextTwo, nextBuilder);
        recursiveBuilder = nextBuilder;
      }
      builder.append(recursiveBuilder);
      return;
    }
    // and now normal evaluation for functions without repeated arguments
    var template = getTemplate(function);
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
    SqlFunctionMapImpl that = (SqlFunctionMapImpl) o;
    return templateByFunction.equals(that.templateByFunction);
  }

  @Override
  public int hashCode() {
    return templateByFunction.hashCode();
  }

  @Override
  public String toString() {
    return "SqlFunctionMapImpl{"
        + "templateByFunction=" + templateByFunction
        + '}';
  }
}
