package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Element;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;

/**
 * ELement builder builds section(s) belonging to given element in Sql builder. Type of builder
 * limits which elements builder can serve.
 *
 * @param <B> is type of sql builder this element builder is able to use
 * @param <T> is type of element this builder is able to work with
 */
public interface ElementTypeSqlBuilder<B extends SqlBuilder<?>, T extends Element<?>> {

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