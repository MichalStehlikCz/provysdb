package com.provys.db.query.elements;

import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents single query in FROM clause.
 */
public interface FromElement extends Element<FromElement> {

  /**
   * Alias associated with this from element. Might actually be either alias or table name,
   * potentially including scheme
   *
   * @return alias associated with this from element
   */
  @Nullable NamePath getAlias();

  /**
   * Indicate if this element matches specified path. Runs match on its alias.
   *
   * @param path is path to be matched
   * @return is this from elements alias (or table name if no alias is specified) matches supplied
   *     path
   */
  default boolean match(NamePath path) {
    var alias = getAlias();
    if (alias == null) {
      return false;
    }
    return alias.match(path);
  }

  /**
   * Verify if given column is available in this data source. It should throw exception in case it
   * knows column does not exist or has incompatible type (including type conversions supported by
   * given source). Checks should only be performed if they are cheap.
   *
   * @param column is column name
   * @param type   is type column should have
   */
  void validateColumn(SimpleName column, Class<?> type);
}
