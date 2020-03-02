package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.SqlElement;

/**
 * Interface represents Sql token - name, symbol, ...
 */
public interface SqlToken extends SqlElement {

  /**
   * Token type.
   *
   * @return token type
   */
  SqlTokenType getTokenType();

  /**
   * Defines if space before is needed.
   *
   * @return if space before is needed
   */
  SpaceMode spaceBefore();

  /**
   * Defines if space after is needed.
   *
   * @return if space after is needed
   */
  SpaceMode spaceAfter();
}
