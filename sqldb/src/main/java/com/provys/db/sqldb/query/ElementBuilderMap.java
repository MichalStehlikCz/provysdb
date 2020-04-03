package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Element;

/**
 * Provides access to element builders for various types of Sql elements.
 *
 * @param <F> is type of factory element builders are suitable for. Type is
 */
public interface ElementBuilderMap<F extends StatementFactory<F>> {

  /**
   * Get element builder, usable for specified element. Report exception when no suitable
   * builder is found.
   *
   * @param element is element we want to build
   * @param <T>        is type of element
   * @return element builder, usable for given element
   */
  <T extends Element<?>> ElementSqlBuilder<? super F, T> getElementBuilder(
      T element);
}
