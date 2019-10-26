package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlparser.SqlParsedToken;

import javax.annotation.Nullable;

/**
 * Represents single token parsed from SQL source. Gives access to type of token and its position in original file,
 * derived classes add additional information (like parsed text)
 */
public abstract class ParsedTokenBase implements SqlParsedToken {

    private final int line;
    private final int pos;

    ParsedTokenBase(int line, int pos) {
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

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParsedTokenBase that = (ParsedTokenBase) o;

        if (line != that.line) return false;
        return pos == that.pos;
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + pos;
        return result;
    }

    @Override
    public String toString() {
        return "SqlTokenBase{" +
                "line=" + line +
                ", pos=" + pos +
                '}';
    }
}
