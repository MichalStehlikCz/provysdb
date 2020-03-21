package com.provys.db.sql;

import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents source - from clause in Sql.
 */
@SuppressWarnings("CyclicClassDependency")// cyclic dependency on factory Context to support cloning
public interface FromClause extends Element {

  /**
   * Get element matching supplied alias. If there are multiple such elements, throw an exception.
   *
   * @param alias is alias used for look-up
   * @return element if exactly one such element exists, null otherwise
   */
  @Nullable FromElement getElementByAlias(NamePath alias);

  /**
   * Check if given alias is unique (or does not exist) in from clause.
   *
   * @param alias is alias to be matched
   * @return true if there is at most one element with specified alias, false if there are multiple
   *     such elements
   */
  boolean isUnique(NamePath alias);

  /**
   * Elements forming from clause.
   *
   * @return list of elements forming this clause
   */
  List<FromElement> getElements();

  /**
   * Transfer from clause to specified context.
   *
   * @param targetContext is target context
   * @param bindMap is new mapping of bind variables; when not specified, bind variables are left as
   *               they are
   * @param <F> is subtype of {@code FromClause} cloning will result in based on target context
   * @return from clause cloned to specified context
   */
  <F extends FromClause> F transfer(Context<?, ?, ?, F, ?, ?, ?> targetContext,
      @Nullable BindMap bindMap);
}
