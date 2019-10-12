package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Instance represents multi-line comment
 */
public class ParsedMultiLineComment extends ParsedTokenBase {

    @Nonnull
    private final String comment;

    ParsedMultiLineComment(int line, int pos, String comment) {
        super(line, pos);
        this.comment = Objects.requireNonNull(comment);
    }

    @Nonnull
    @Override
    public SqlTokenType getType() {
        return SqlTokenType.COMMENT;
    }

    @Override
    public void append(CodeBuilder builder) {
        builder.append("/*").append(comment).append("*/");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParsedMultiLineComment that = (ParsedMultiLineComment) o;

        return comment.equals(that.comment);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + comment.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SqlMultiLineComment{" +
                "comment='" + comment + '\'' +
                "} " + super.toString();
    }
}
