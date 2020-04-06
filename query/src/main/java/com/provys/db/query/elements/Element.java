package com.provys.db.query.elements;

import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import java.util.Collection;

/**
 * Common interface for all Sql element data objects.
 */
public interface Element<T extends Element<T>> {

  /**
   * Retrieve collection of bind variables, referenced from given element.
   *
   * @return bind variables referenced from given element
   */
  Collection<BindVariable> getBinds();

  /**
   * Clone element, replacing binds based on supplied bind map. It is assumed that provided
   * replacement bind variable is compatible with given element; if not, method will fail.
   *
   * @param bindMap is map defining bind variables that should replace variable with given name
   * @return element that is almost the same, but with bind variables replaced based on supplied map
   */
  T mapBinds(BindMap bindMap);

  /**
   * Apply this element on supplied consumer. Finds proper method this element is able to translate
   * to and call it.
   *
   * @param consumer is query consumer, supplying methods that allow to apply content of element
   */
  void apply(QueryConsumer consumer);
}
