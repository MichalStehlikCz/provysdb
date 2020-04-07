package com.provys.db.querybuilder;

import com.provys.provysdb.sql.NamePath;

/**
 * Class holds name of table alias. It can be either name or alias in form {@code <<int>>}, where
 * int is integer; this form allows replacement with unique alias during select text preparation
 */
public interface QueryAlias extends NamePath {

  /**
   * Alias, might also be alias placeholder.
   *
   * @return alias (potentially in form {@code <<int>>})
   */
  String getAlias();
}
