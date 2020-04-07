package com.provys.db.sqlquery.query;

import static com.provys.db.query.elements.Function.ANY_NVL;
import static com.provys.db.query.elements.Function.DATE_SYSDATE;
import static com.provys.db.query.elements.Function.STRING_CHR;
import static com.provys.db.query.elements.Function.STRING_CONCAT;

import com.provys.common.exception.InternalException;
import com.provys.db.query.elements.Function;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import java.util.ArrayList;
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

  @Override
  public <B extends SqlBuilder<B>> void append(Function function,
      List<? extends Consumer<? super B>> argumentAppend, B builder) {
    var template = getTemplate(function);
    var matcher = ARGUMENT_PATTERN.matcher(template);
    int pos = 0;
    if (function.lastArgumentRepeatable() && (argumentAppend.size() > 2)) {
      // in case of repeatable last argument, we evaluate template repeatedly
      // we create list with all but first argument, that will be used to append second argument
      var afterFirst = argumentAppend.subList(1, argumentAppend.size());
      while (matcher.find()) {
        builder.append(template.substring(pos, matcher.start()));
        var argIndex = Integer.parseInt(template.substring(matcher.start() + 1, matcher.end() - 1));
        switch (argIndex) {
          case 0:
            // append first argument
            argumentAppend.get(0).accept(builder);
            break;
          case 1:
            // append statement for rest of arguments
            append(function, afterFirst, builder);
            break;
          default:
            throw new InternalException("Invalid template " + template
                + "; repeatable function should only have two arguments");
        }
        pos = matcher.end();
      }
    } else {
      // and now normal evaluation for functions without repeated arguments
      while (matcher.find()) {
        builder.append(template.substring(pos, matcher.start()));
        var argIndex = Integer.parseInt(template.substring(matcher.start() + 1, matcher.end() - 1));
        argumentAppend.get(argIndex).accept(builder);
        pos = matcher.end();
      }
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
