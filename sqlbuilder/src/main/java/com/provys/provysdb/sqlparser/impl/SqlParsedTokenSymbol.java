package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlparser.SqlParsedToken;

import javax.annotation.Nonnull;

public interface SqlParsedTokenSymbol extends SqlParsedToken {

    /**
     * @return symbol this token represents
     */
    @Nonnull
    String getSymbol();
}
