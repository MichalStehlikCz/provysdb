package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.Select;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

/**
 * Analyses statement, builds context based on that, passes through elements and builds resulting
 * statement.
 */
public interface SqlBuilder<F extends StatementFactory, Q extends Select,
    S extends SelectStatement> {

  /**
   * Factory used by this Sql builder. Gives access to function map etc.
   *
   * @return used statement factory
   */
  F getStatementFactory();

  /**
   * Select query this builder is used for.
   *
   * @return select query this builder is used for
   */
  Q getQuery();

  /**
   * Access code builder that is used to build text and collect binds for this SqlBuilder.
   *
   * @return code builder used to collect text and binds for this sql builder
   */
  CodeBuilder getCodeBuilder();

  /**
   * Append expression to internal code builder. Allows composed expressions to build
   * sub-expressions
   *
   * @param expression is expression to be appended
   * @return self to support fluent build
   */
  SqlBuilder<F, Q, S> append(Expression<?> expression);

  /**
   * Build statement based on supplied select query.
   *
   * @return new statement, built on supplied statement
   */
  S build();
}
