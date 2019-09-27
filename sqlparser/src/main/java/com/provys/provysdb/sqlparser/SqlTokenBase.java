package com.provys.provysdb.sqlparser;

/**
 * Represents single token parsed from SQL source. Gives access to type of token and its position in original file,
 * derived classes add additional information (like parsed text)
 */
public abstract class SqlTokenBase implements SqlToken {

    private final int line;
    private final int pos;

    SqlTokenBase(int line, int pos) {
        this.line = line;
        this.pos = pos;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getPos() {
        return pos;
    }
}
