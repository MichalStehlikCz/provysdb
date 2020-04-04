package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Expression;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;

public interface ExpressionSqlBuilder<B extends SqlBuilder<?>> {

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param expression is element that is being appended
   */
  void append(B sqlBuilder, Expression<?> expression);
}
