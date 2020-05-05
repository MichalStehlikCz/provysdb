package com.provys.db.sqlquery.query;

import com.provys.db.query.functions.BuiltIn;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Allows to either build {@link SqlBuiltInMap} from ground up or adopt existing function map,
 * modify selected function appenders and build new one.
 */
public final class SqlBuiltInMapBuilder {

  private final Map<BuiltIn, SqlBuiltInAppender> appenderByBuiltIn;

  /**
   * Create empty {@code SqlFunctionMapBuilder}.
   */
  public SqlBuiltInMapBuilder() {
    this.appenderByBuiltIn = new ConcurrentHashMap<>(30);
  }

  /**
   * Create {@code SqlFunctionMapBuilder} with initial mapping based on supplied function map.
   *
   * @param source is function map used as source for initial mapping
   */
  public SqlBuiltInMapBuilder(SqlBuiltInMap source) {
    this.appenderByBuiltIn = new ConcurrentHashMap<>(source.getAppenderByBuiltIn());
  }

  /**
   * Change mapping of specified function to provided template. Does not complain about mapping
   * being changed repeatedly
   *
   * @param builtIn is function or condition oeprator we want to map
   * @param appender is template that should be used for given function
   * @return self to support fluent build
   */
  public SqlBuiltInMapBuilder put(BuiltIn builtIn, SqlBuiltInAppender appender) {
    appenderByBuiltIn.put(builtIn, appender);
    return this;
  }

  /**
   * Build function map based on mappings, kept in this builder.
   *
   * @return function map with mappings, present in this builder
   */
  public SqlBuiltInMap build() {
    return new SqlBuiltInMapImpl(appenderByBuiltIn);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlBuiltInMapBuilder that = (SqlBuiltInMapBuilder) o;
    return Objects.equals(appenderByBuiltIn, that.appenderByBuiltIn);
  }

  @Override
  public int hashCode() {
    return appenderByBuiltIn != null ? appenderByBuiltIn.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlBuiltInMapBuilder{"
        + "appenderByBuiltIn=" + appenderByBuiltIn
        + '}';
  }
}
