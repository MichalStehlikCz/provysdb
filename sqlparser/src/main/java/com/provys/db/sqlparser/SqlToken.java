package com.provys.db.sqlparser;

import com.provys.db.query.elements.Element;

/**
 * Interface represents Sql token - name, symbol, ...
 */
public interface SqlToken extends Element<SqlToken> {

  /**
   * Token type.
   *
   * @return token type
   */
  SqlTokenType getTokenType();
}
