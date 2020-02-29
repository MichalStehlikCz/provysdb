package com.provys.provysdb.sqlbuilder;

import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents column definition in select statement.
 */
public interface SqlColumn extends SqlElement {

  /**
   * Alias this column is associated with.  Note that if it is simple column, its name is also used
   * as alias
   *
   * @return alias this column is associated with. Note that if it is simple column, its name is
   * also used as alias
   */
  @Nullable SqlIdentifier getAlias();

  /**
   * Alias this column is associated with, Optional version.
   *
   * @return alias this column is associated with, empty optional if alias is absent
   */
  default Optional<SqlIdentifier> getOptAlias() {
    return Optional.ofNullable(getAlias());
  }

  /**
   * Create new column with alias replaced with specified one.
   *
   * @param alias is alias that should be used for new column
   * @return column with specified alias
   */
  SqlColumn withAlias(SqlIdentifier alias);
}
