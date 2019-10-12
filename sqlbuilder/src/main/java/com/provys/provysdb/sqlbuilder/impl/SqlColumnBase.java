package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

abstract class SqlColumnBase implements SqlColumn {
    @Nullable
    private final SqlIdentifier alias;

    SqlColumnBase(@Nullable SqlIdentifier alias) {
        this.alias = alias;
    }

    @Nonnull
    @Override
    public Optional<SqlIdentifier> getAlias() {
        return Optional.ofNullable(alias);
    }
}
