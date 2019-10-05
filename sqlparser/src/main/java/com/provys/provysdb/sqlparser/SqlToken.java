package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

/**
 * Represents token parsed from Sql source. Usually retrieved via tokenizer
 */
public interface SqlToken {
    /**
     * @return line of source token was parsed from
     */
    int getLine();

    /**
     * @return position in line where token starts
     */
    int getPos();

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
