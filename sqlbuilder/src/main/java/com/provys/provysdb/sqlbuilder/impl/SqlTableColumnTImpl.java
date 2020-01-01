package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class SqlTableColumnTImpl<T> implements SqlTableColumnT<T> {

    @Nonnull
    private final SqlTableColumn column;
    @Nonnull
    private final Class<T> type;

    SqlTableColumnTImpl(SqlTableColumn column, Class<T> type) {
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
    public SqlTableColumnT<T> withAlias(SqlIdentifier alias) {
        var baseColumn = column.withAlias(alias);
        if (baseColumn == column) {
            return this;
        }
        return new SqlTableColumnTImpl<>(baseColumn, type);
    }

    @Nonnull
    @Override
    public SqlTableColumnT<T> withTableAlias(SqlTableAlias tableAlias) {
        var baseColumn = column.withTableAlias(tableAlias);
        if (baseColumn == column) {
            return this;
        }
        return new SqlTableColumnTImpl<>(baseColumn, type);
    }
}
