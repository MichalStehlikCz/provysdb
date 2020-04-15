package com.provys.db.sqlquery.query;

import com.provys.db.query.functions.BuiltInFunction;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Supports translation of function expression to its com.provys.db.sql representation via
 * templates.
 */
public interface SqlFunctionMap {

  /**
   * Map this function map is based on. It is strongly recommended to use {@code getTemplate} to
   * retrieve mapping, but this function can be used to adapt existing map via {@link
   * SqlFunctionMapBuilder}
   *
   * @return map this function map is based on
   */
  Map<BuiltInFunction, SqlFunctionAppender> getAppenderByFunction();

  /**
   * Get appender usable for given function.
   *
   * @param function is function we want to get appender for
   * @return appender that will apply function with given arguments to builder
   */
  SqlFunctionAppender getAppender(BuiltInFunction function);

  /**
   * Append evaluated function to builder.
   *
   * @param function       is function to be applied
   * @param argumentAppend is procedure that appends given argument to builder
   * @param builder        is builder where whole expression should be appended
   */
  <B extends SqlBuilder<B>> void append(BuiltInFunction function,
      List<? extends Consumer<? super B>> argumentAppend, B builder);
}
