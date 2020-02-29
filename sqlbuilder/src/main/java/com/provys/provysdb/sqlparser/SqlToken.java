package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.SqlElement;

/**
 * Interface represents Sql token - name, symbol, ...
 */
public interface SqlToken extends SqlElement {

  /**
   * @return token type
   */
  SqlTokenType getTokenType();

  /**
   * @return if space before is needed
   */
  SpaceMode spaceBefore();

  /**
   * @return if space after is needed
   */
  SpaceMode spaceAfter();
}
