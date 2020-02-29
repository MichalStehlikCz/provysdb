package com.provys.provysdb.sqlbuilder;

import java.util.Collection;

/**
 * Class represents sql statement, described in structured way. This statement can be used to
 * initiate {@code DbPreparedStatement} object for execution
 */
public interface Select {

  /**
   * Sql select text built from select statement.
   *
   * @return sql select text built from this select statement
   */
  String getSqlText();

  /**
   * Collection of bind variables used in statement.
   *
   * @return collection of bind variables used in this statement
   */
  Collection<BindName> getBinds();
}
