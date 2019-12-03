package com.provys.provysdb.sqlparser;

/**
 * Represents token parsed from Sql source, usually retrieved via tokenizer. Extends token, adds information about
 * position in original source
 */
public interface SqlParsedToken extends SqlToken {
    /**
     * @return line of source token was parsed from
     */
    int getLine();

    /**
     * @return position in line where token starts
     */
    int getPos();
}
