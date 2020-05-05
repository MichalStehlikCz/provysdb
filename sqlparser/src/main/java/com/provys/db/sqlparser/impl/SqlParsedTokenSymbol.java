package com.provys.db.sqlparser.impl;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.sqlparser.SqlParsedToken;
import com.provys.db.sqlparser.SqlSymbol;

@Immutable
interface SqlParsedTokenSymbol extends SqlParsedToken {

  /**
   * Symbol this token represents.
   *
   * @return symbol this token represents
   */
  SqlSymbol getSymbol();
}
