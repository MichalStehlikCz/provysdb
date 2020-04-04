package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Condition;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;

public interface ConditionSqlBuilder<B extends SqlBuilder<?>> {

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param condition  is element that is being appended
   */
  void append(B sqlBuilder, Condition condition);
}
