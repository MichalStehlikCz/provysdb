package com.provys.db.sqlparser.impl;

import com.provys.db.sqlparser.SqlParsedToken;
import com.provys.db.sqlparser.SqlSymbol;

interface SqlParsedTokenSymbol extends SqlParsedToken {

  /**
   * Symbol this token represents.
   *
   * @return symbol this token represents
   */
  SqlSymbol getSymbol();
}
