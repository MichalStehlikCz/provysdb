package sqlparser.impl;

import sqlparser.SqlParsedToken;
import sqlparser.SqlSymbol;

interface SqlParsedTokenSymbol extends SqlParsedToken {

  /**
   * Symbol this token represents.
   *
   * @return symbol this token represents
   */
  SqlSymbol getSymbol();
}
