package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.SimpleName;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
public interface SelectClause extends Element<SelectClause> {

  /**
   * Find column with specified alias in select clause.
   *
   * @param alias is alias column should have
   * @return column with specified alias if it exists, null if such column doesn't exist
   */
  @Nullable SelectColumn<?> getColumnByAlias(SimpleName alias);
}
