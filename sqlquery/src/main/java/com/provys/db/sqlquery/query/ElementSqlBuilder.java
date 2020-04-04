package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Element;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;

/**
 * Provides access to element builders for various types of Sql elements.
 *
 * @param <B> is type of builder this element map can be used on
 */
public interface ElementSqlBuilder<B extends SqlBuilder<?>> {

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param element    is element that is being appended
   */
  void append(B sqlBuilder, Element<?> element);
}
