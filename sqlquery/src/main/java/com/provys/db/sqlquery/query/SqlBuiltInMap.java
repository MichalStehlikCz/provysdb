package com.provys.db.sqlquery.query;

import com.provys.db.query.functions.BuiltIn;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Supports translation of function expression to its com.provys.db.sql representation via
 * templates.
 */
public interface SqlBuiltInMap {

  /**
   * Map this function map is based on. It is strongly recommended to use {@code getTemplate} to
   * retrieve mapping, but this function can be used to adapt existing map via {@link
   * SqlBuiltInMapBuilder}
   *
   * @return map this function map is based on
   */
  Map<BuiltIn, SqlBuiltInAppender> getAppenderByBuiltIn();

  /**
   * Get appender usable for given function.
   *
   * @param builtIn is function we want to get appender for
   * @return appender that will apply function with given arguments to builder
   */
  SqlBuiltInAppender getAppender(BuiltIn builtIn);

  /**
   * Append evaluated function to builder.
   *
   * @param builtIn       is function to be applied
   * @param argumentAppend is procedure that appends given argument to builder
   * @param builder        is builder where whole expression should be appended
   * @param <B>            is type of builder passed to method; used to ensure that argument
   *                       appender functions accept supplied builder
   */
  <B extends SqlBuilder<B>> void append(BuiltIn builtIn,
      List<? extends Consumer<? super B>> argumentAppend, B builder);
}
