package com.provys.db.sqlquery.query;

import com.provys.db.query.functions.BuiltInFunction;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Allows to either build {@link SqlFunctionMap} from ground up or adopt existing function map,
 * modify selected function appenders and build new one.
 */
public final class SqlFunctionMapBuilder {

  private final Map<BuiltInFunction, SqlFunctionAppender> appenderByFunction;

  /**
   * Create empty {@code SqlFunctionMapBuilder}.
   */
  public SqlFunctionMapBuilder() {
    this.appenderByFunction = new ConcurrentHashMap<>(30);
  }

  /**
   * Create {@code SqlFunctionMapBuilder} with initial mapping based on supplied function map.
   *
   * @param source is function map used as source for initial mapping
   */
  public SqlFunctionMapBuilder(SqlFunctionMap source) {
    this.appenderByFunction = new ConcurrentHashMap<>(source.getAppenderByFunction());
  }

  /**
   * Change mapping of specified function to provided template. Does not complain about mapping
   * being changed repeatedly
   *
   * @param function is function we want to map
   * @param appender is template that should be used for given function
   * @return self to support fluent build
   */
  public SqlFunctionMapBuilder put(BuiltInFunction function, SqlFunctionAppender appender) {
    appenderByFunction.put(function, appender);
    return this;
  }

  /**
   * Build function map based on mappings, kept in this builder.
   *
   * @return function map with mappings, present in this builder
   */
  public SqlFunctionMap build() {
    return new SqlFunctionMapImpl(appenderByFunction);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFunctionMapBuilder that = (SqlFunctionMapBuilder) o;
    return Objects.equals(appenderByFunction, that.appenderByFunction);
  }

  @Override
  public int hashCode() {
    return appenderByFunction != null ? appenderByFunction.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlFunctionMapBuilder{"
        + "appenderByFunction=" + appenderByFunction
        + '}';
  }
}
