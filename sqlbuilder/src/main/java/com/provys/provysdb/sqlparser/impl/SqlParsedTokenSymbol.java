package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlparser.SqlParsedToken;
import com.provys.provysdb.sqlparser.SqlSymbol;

import javax.annotation.Nonnull;

interface SqlParsedTokenSymbol extends SqlParsedToken {

    /**
     * @return symbol this token represents
     */
    @Nonnull
    SqlSymbol getSymbol();
}
