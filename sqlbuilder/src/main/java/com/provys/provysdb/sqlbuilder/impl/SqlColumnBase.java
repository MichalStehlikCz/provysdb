package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

abstract class SqlColumnBase implements SqlColumn {
    @Nullable
    private final SqlName alias;

    SqlColumnBase(@Nullable SqlName alias) {
        this.alias = alias;
    }

    @Nonnull
    @Override
    public Optional<SqlName> getAlias() {
        return Optional.ofNullable(alias);
    }
}
