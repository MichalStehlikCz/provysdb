package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) {
            return false;
        }

        SqlSingleLineComment that = (SqlSingleLineComment) o;
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
