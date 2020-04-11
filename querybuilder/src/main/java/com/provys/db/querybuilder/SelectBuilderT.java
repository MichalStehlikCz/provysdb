package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.FromElement;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Ancestor for all select builder classes, including SelectBuilderT0 that does not allow
 * production of Select statement.
 *
 * @param <T> is actual implementation of select builder
 */
interface SelectBuilderT<T extends SelectBuilderT<T>> {

  /**
   * Add from element to from clause.
   *
   * @param fromElement is element to be added
   * @return builder with given from element added
   */
  T from(FromElement fromElement);

  /**
   * Add from element, based on supplied table and optionally using supplied alias.
   *
   * @param tableName is name, identifying data source
   * @param alias     is alias to be used by columns to refer to this source
   * @return builder with specified from element added
   */
  T fromTable(NamePath tableName, @Nullable SimpleName alias);

  /**
   * Add from element, based on supplied table.
   *
   * @param tableName is name, identifying data source
   * @return builder with specified from element added
   */
  default T fromTable(NamePath tableName) {
    return fromTable(tableName, null);
  }

  /**
   * Add from element, based on select statement.
   *
   * @param select is select statement from element is based on
   * @param alias  is alias used to refer to this from element
   * @return builder with specified from element added
   */
  T fromSelect(SelectT<?> select, @Nullable SimpleName alias);

  /**
   * Add from element, based on select statement.
   *
   * @param select is select statement from element is based on
   * @return builder with specified from element added
   */
  default T fromSelect(SelectT<?> select) {
    return fromSelect(select, null);
  }

  /**
   * Add from element, based on dual pseudo-table (or however no table in from is
   * represented).
   *
   * @param alias is alias used to refer to this from element
   * @return builder with specified from element added
   */
  T fromDual(@Nullable SimpleName alias);

  /**
   * Add from element, based on dual pseudo-table (or however no table in from is
   * represented).
   *
   * @return builder with specified from element added
   */
  default T fromDual() {
    return fromDual(null);
  }

  /**
   * Add condition to where clause.
   *
   * @param condition is condition to be added
   * @return builder with given condition added
   */
  T where(Condition condition);
}
