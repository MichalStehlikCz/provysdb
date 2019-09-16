package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;

import javax.annotation.Nonnull;
import java.util.Objects;

abstract class SqlFromBase implements SqlFrom {
    @Nonnull
    private final SqlTableAlias alias;

    SqlFromBase(SqlTableAlias alias) {
        this.alias = Objects.requireNonNull(alias);
    }

    @Nonnull
    @Override
    public SqlTableAlias getAlias() {
        return alias;
    }
}
