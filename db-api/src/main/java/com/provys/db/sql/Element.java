package com.provys.db.sql;

import java.util.Collection;

/**
 * Common properties of all sql elements; they allow to verify validity of elements during build.
 */
@SuppressWarnings("CyclicClassDependency")// cyclic dependency on factory Context to support cloning
public interface Element {

  /**
   * Retrieve context element is valid for.
   *
   * @return sql context given element is valid for
   */
  Context<?, ?, ?, ?, ?, ?, ?> getContext();

  /**
   * Retrieve collection of bind variables, referenced from given element.
   *
   * @return bind variables referenced from given element
   */
  Collection<BindVariable> getBinds();
}
