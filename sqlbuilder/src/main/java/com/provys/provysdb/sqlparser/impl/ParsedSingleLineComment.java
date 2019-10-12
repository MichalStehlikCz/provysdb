package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents single line comment in Sql code
 */
class ParsedSingleLineComment extends ParsedTokenBase {

    @Nonnull
    private final String text;

    ParsedSingleLineComment(int line, int pos, String text) {
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
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) {
            return false;
        }

        ParsedSingleLineComment that = (ParsedSingleLineComment) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return super.hashCode()*31 + text.hashCode();
    }

    @Override
    public String toString() {
        return "SqlSingleLineComment{" +
                "text='" + text + '\'' +
                "} " + super.toString();
    }
}
