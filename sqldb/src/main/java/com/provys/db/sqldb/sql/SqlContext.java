package com.provys.db.sqldb.sql;

import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.Function;
import java.util.List;
import java.util.function.Consumer;

/**
 * {@link Context} specialisation that overrides factory methods with variants, returning {@link
 * SqlElement} versions of elements.
 */
public interface SqlContext<S extends SqlSelect, A extends SqlSelectClause,
    C extends SqlSelectColumn, F extends SqlFromClause, J extends SqlFromElement,
    W extends SqlCondition, E extends SqlExpression> extends
    Context<S, A, C, SqlFromClause, J, W, E> {

  /**
   * Get template usable for given function.
   *
   * @param function is function we want to get template for
   * @return template, with {i} used as placeholder for i-th argument
   */
  String getFunctionTemplate(Function function);

  /**
   * Append evaluated template to builder.
   *
   * @param function       is function to be applied
   * @param argumentAppend is procedure that appends given argument to builder
   * @param builder        is builder where whole expression should be appended
   */
  void append(Function function, List<? extends Consumer<CodeBuilder>> argumentAppend,
      CodeBuilder builder);
}
