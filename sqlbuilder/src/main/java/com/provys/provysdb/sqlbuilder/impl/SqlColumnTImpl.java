package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

class SqlColumnTImpl<T> implements SqlColumnT<T> {

    @Nonnull
    private final SqlColumn column;
    @Nonnull
    private final Class<T> type;

    SqlColumnTImpl(SqlColumn column, Class<T> type) {
        this.column = Objects.requireNonNull(column);
        this.type = Objects.requireNonNull(type);
    }

    @Nonnull
    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    @Nonnull
    public Optional<SqlIdentifier> getAlias() {
        return column.getAlias();
    }

    @Override
    public void addSql(CodeBuilder builder) {
        column.addSql(builder);
    }

    @Nonnull
    @Override
    public SqlColumnT<T> withAlias(SqlIdentifier alias) {
        if (alias.equals(getAlias().orElse(null))) {
            return this;
        }
        return new SqlColumnTImpl<>(column.withAlias(alias), type);
    }
}
