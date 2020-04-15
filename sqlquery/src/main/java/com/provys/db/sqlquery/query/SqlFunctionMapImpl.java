package com.provys.db.sqlquery.query;

import static com.provys.db.query.functions.BuiltInFunction.ANY_NVL;
import static com.provys.db.query.functions.BuiltInFunction.DATE_SYSDATE;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CHR;
import static com.provys.db.query.functions.BuiltInFunction.STRING_CONCAT;

import com.provys.db.query.functions.BuiltInFunction;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SqlFunctionMapImpl implements SqlFunctionMap {

  private static final SqlFunctionMapImpl DEFAULT = new SqlFunctionMapImpl(Map.of(
      STRING_CHR, new SqlTemplateAppender("CHR({0})"),
      STRING_CONCAT, SqlRecursiveAppender.forTemplate("{0}||{1}"),
      DATE_SYSDATE, new SqlTemplateAppender("SYSDATE"),
      ANY_NVL, new SqlTemplateAppender("NVL({0}, {1})")
  ));

  public static SqlFunctionMapImpl getDefault() {
    return DEFAULT;
  }

  private final Map<BuiltInFunction, SqlFunctionAppender> appenderByFunction;

  public SqlFunctionMapImpl(Map<BuiltInFunction, SqlFunctionAppender> appenderByFunction) {
    this.appenderByFunction = Map.copyOf(appenderByFunction);
  }

  @Override
  public Map<BuiltInFunction, SqlFunctionAppender> getAppenderByFunction() {
    return appenderByFunction;
  }

  @Override
  public SqlFunctionAppender getAppender(BuiltInFunction function) {
    var result = appenderByFunction.get(function);
    if (result == null) {
      throw new NoSuchElementException("Appender not found for function " + function);
    }
    return result;
  }

  @Override
  public <B extends SqlBuilder<B>> void append(BuiltInFunction function,
      List<? extends Consumer<? super B>> argumentAppend, B builder) {
    getAppender(function).append(argumentAppend, builder);
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
    return appenderByFunction.equals(that.appenderByFunction);
  }

  @Override
  public int hashCode() {
    return appenderByFunction.hashCode();
  }

  @Override
  public String toString() {
    return "SqlFunctionMapImpl{"
        + "appenderByFunction=" + appenderByFunction
        + '}';
  }
}
