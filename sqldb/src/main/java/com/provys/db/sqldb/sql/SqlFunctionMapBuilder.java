package com.provys.db.sqldb.sql;

import com.provys.db.sql.Function;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Allows to either build {@link SqlFunctionMap} from ground up or adopt existing function map,
 * modify selected function templates and build new one.
 */
public class SqlFunctionMapBuilder {

  private final Map<Function, String> templateByFunction;

  /**
   * Create empty {@code SqlFunctionMapBuilder}.
   */
  public SqlFunctionMapBuilder() {
    this.templateByFunction = new ConcurrentHashMap<>(30);
  }

  /**
   * Create {@code SqlFunctionMapBuilder} with initial mapping based on supplied function map.
   *
   * @param source is function map used as source for initial mapping
   */
  public SqlFunctionMapBuilder(SqlFunctionMap source) {
    this.templateByFunction = new ConcurrentHashMap<>(source.getTemplateByFunction());
  }

  /**
   * Change mapping of specified function to provided template. Does not complain about mapping
   * being changed repeatedly
   *
   * @param function is function we want to map
   * @param template is template that should be used for given function
   * @return self to support fluent build
   */
  public SqlFunctionMapBuilder put(Function function, String template) {
    templateByFunction.put(function, template);
    return this;
  }

  /**
   * Build function map based on mappings, kept in this builder.
   *
   * @return function map with mappings, present in this builder
   */
  public SqlFunctionMap build() {
    return new SqlFunctionMapImpl(templateByFunction);
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
    return Objects.equals(templateByFunction, that.templateByFunction);
  }

  @Override
  public int hashCode() {
    return templateByFunction != null ? templateByFunction.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlFunctionMapBuilder{"
        + "templateByFunction=" + templateByFunction
        + '}';
  }
}
