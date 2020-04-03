package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Element;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

/**
 * Provides access to element builders for various types of Sql elements.
 *
 * @param <B> is type of builder this element map can be used on
 */
public interface ElementSqlBuilderMap<B extends SqlBuilder<?>> {

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param element    is element that is being appended
   */
  void append(B sqlBuilder, Element<?> element);
}
