package com.provys.provysdb.sqlparser;

import javax.annotation.Nullable;

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

    /**
     * Accepts subclasses
     * @param o
     * @return
     */
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlTokenBase that = (SqlTokenBase) o;

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
