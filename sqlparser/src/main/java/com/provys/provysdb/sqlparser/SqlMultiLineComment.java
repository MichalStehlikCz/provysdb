package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Instance represents multi-line comment
 */
public class SqlMultiLineComment extends SqlTokenBase {

    @Nonnull
    private final String comment;

    SqlMultiLineComment(int line, int pos, String comment) {
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
}
