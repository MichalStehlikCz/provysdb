package com.provys.provysdb.sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents table identification. Consists of table name and optional schema.
 */
public interface TableSpec extends SpecPath {

  /**
   * Schema table is in. Might be unspecified if table is in the current scheme or if table has
   * public synonym (as is mostly case in Provys)
   *
   * @return scheme part of table identification, null if not specified
   */
  @Nullable Identifier getSchema();

  /**
   * Table name or synonym pointing to table name.
   *
   * @return table name
   */
  Identifier getName();
}
