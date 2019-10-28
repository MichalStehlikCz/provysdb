package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlKeyword;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import java.util.Objects;

class ParsedKeyword extends ParsedTokenBase {

    private final SqlKeyword keyword;

    ParsedKeyword(int line, int pos, SqlKeyword keyword) {
        super(line, pos);
        this.keyword = Objects.requireNonNull(keyword);
    }


    @Nonnull
    @Override
    public SqlTokenType getType() {
        return SqlTokenType.KEYWORD;
    }

    /**
     * @return value of field keyword
     */
    SqlKeyword getKeyword() {
        return keyword;
    }

    @Override
    public SpaceMode spaceBefore() {
        return SpaceMode.FORCE;
    }

    @Override
    public SpaceMode spaceAfter() {
        return SpaceMode.FORCE;
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append(keyword.toString());
    }
}
