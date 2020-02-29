package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlparser.SqlParsedToken;
import com.provys.provysdb.sqlparser.SqlSymbol;

interface SqlParsedTokenSymbol extends SqlParsedToken {

  /**
   * Symbol this token represents.
   *
   * @return symbol this token represents
   */
  SqlSymbol getSymbol();
}
