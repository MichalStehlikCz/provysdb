package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Expression;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

/**
 * Expression builder allows to append expression text to code builder.
 *
 * @param <F> is specialisation of statement factory this builder can work for
 * @param <T> is type of expression builder can handle
 */
public interface ExpressionBuilder<F extends StatementFactory, T extends Expression<?>> {

  /**
   * Type of expression this builder can handle.
   *
   * @return type of expression this builder can handle
   */
  Class<T> getType();

  /**
   * Append sql text, associated with supplied statement.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                  appended to
   * @param expression is expression that is being appended
   */
  void append(SqlBuilder<? extends F, ?, ?> sqlBuilder, T expression);
}
