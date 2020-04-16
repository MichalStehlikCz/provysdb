package com.provys.db.sqlquery.query;

import com.google.errorprone.annotations.Immutable;
import java.util.List;
import java.util.function.Consumer;

/**
 * Description that can be used to build function Sql text.
 */
@FunctionalInterface
@Immutable
public interface SqlBuiltInAppender {

  /**
   * Append function this appender belongs to builder.
   *
   * @param argumentAppend is procedure that appends given argument to builder
   * @param builder        is builder where whole expression should be appended
   * @param <B>            is type of builder passed to method; used to ensure that argument
   *                       appender functions accept supplied builder
   */
  <B extends SqlBuilder<B>> void append(List<? extends Consumer<? super B>> argumentAppend,
      B builder);
}
