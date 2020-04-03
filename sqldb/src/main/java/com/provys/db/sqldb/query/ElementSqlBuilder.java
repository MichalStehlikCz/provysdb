package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Element;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

public interface ElementSqlBuilder<B extends SqlBuilder, T extends Element<?>> {

  /**
   * Type of element this builder can handle.
   *
   * @return type of element this builder can handle
   */
  Class<T> getType();

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param element    is element that is being appended
   */
  void append(B sqlBuilder, T element);
}