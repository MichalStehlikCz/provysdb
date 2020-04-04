package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.SelectColumn;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;

public interface SelectColumnSqlBuilder<B extends SqlBuilder<?>> {

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param column     is element that is being appended
   */
  void append(B sqlBuilder, SelectColumn<?> column);
}
