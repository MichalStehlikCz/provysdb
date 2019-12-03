package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

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
}
