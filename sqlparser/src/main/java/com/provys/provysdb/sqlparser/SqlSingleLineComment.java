package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

/**
 * Represents single line comment in Sql code
 */
class SqlSingleLineComment extends SqlTokenBase {

    @Nonnull
    private final String text;

    SqlSingleLineComment(int line, int pos, String text) {
        super(line, pos);
        this.text = text;
    }

    @Nonnull
    @Override
    public SqlTokenType getType() {
        return SqlTokenType.COMMENT;
    }

    @Override
    public void append(CodeBuilder builder) {
        builder.append("--").appendLine(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlSingleLineComment that = (SqlSingleLineComment) o;

        return (getLine() == that.getLine()) &&
                (getPos() == that.getPos()) &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
