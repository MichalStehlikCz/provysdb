package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

/**
 * Interface represents Sql token - name, symbol, ...
 */
public interface SqlToken {

    /**
     * @return token type
     */
    @Nonnull
    SqlTokenType getType();

    /**
     * Append token to code builder
     *
     * @param builder is code builder token should be appended to
     */
    void append(CodeBuilder builder);
}
