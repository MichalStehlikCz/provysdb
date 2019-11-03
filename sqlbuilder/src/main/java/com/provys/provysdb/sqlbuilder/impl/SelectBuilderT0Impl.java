package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SelectBuilderT0Impl extends SelectBuilderTImpl<SelectBuilderT0Impl>
        implements SelectBuilderT0 {

    SelectBuilderT0Impl(Sql sql) {
        super(sql);
    }

    SelectBuilderT0Impl(Sql sql, List<SqlFrom> tables, Collection<Condition> conditions) {
        super(sql, List.of(), tables, conditions);
    }

    @Nonnull
    @Override
    SelectBuilderT0Impl self() {
        return this;
    }

    @Nonnull
    @Override
    public List<SqlColumn> getColumns() {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    protected List<SqlColumn> getModifiableColumns() {
        return List.of();
    }

    @Nonnull
    @Override
    public SelectBuilderT0Impl copy() {
        return new SelectBuilderT0Impl(sql, tables, conditions);
    }

    @Nonnull
    private <T> SelectBuilderT1<T> column(SqlColumnT<T> column) {
        return new SelectBuilderT1Impl<>(sql, column, tables, conditions);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(SqlIdentifier column, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(column, clazz));
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), column, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(column, alias, clazz));
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias,
                                         Class<T> clazz) {
        return column(sql.column(tableAlias, column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(String columnName, Class<T> clazz) {
        if (tables.isEmpty()) {
            return column(sql.column(columnName, clazz));
        }
        return column(sql.column(tables.get(tables.size()-1).getAlias(), sql.name(columnName), clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return column(sql.column(tableAlias, columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(sql.column(tableAlias, columnName, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String columnSql, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return column(sql.columnDirect(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return column(sql.columnDirect(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, Class<T> clazz) {
        return column(sql.columnSql(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return column(sql.columnSql(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, String alias, Class<T> clazz, BindVariable... binds) {
        return column(sql.columnSql(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, String alias, Iterable<BindVariable> binds, Class<T> clazz) {
        return column(sql.columnSql(columnSql, alias, binds, clazz));
    }
}
