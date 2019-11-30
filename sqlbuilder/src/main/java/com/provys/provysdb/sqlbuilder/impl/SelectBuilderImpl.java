package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.*;

class SelectBuilderImpl extends SelectBuilderTImpl<SelectBuilderImpl> implements SelectBuilder {

    private final List<SqlColumn> columns;

    SelectBuilderImpl(DbSql sql) {
        super(sql);
        columns = new ArrayList<>(5);
    }

    SelectBuilderImpl(DbSql sql, List<SqlColumn> columns, List<SqlFrom> tables, Collection<Condition> conditions) {
        super(sql, columns, tables, conditions);
        this.columns = new ArrayList<>(columns);
    }

    @Nonnull
    @Override
    SelectBuilderImpl self() {
        return this;
    }

    @Nonnull
    @Override
    public List<SqlColumn> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    @Nonnull
    @Override
    public SelectBuilder columnUntyped(SqlColumn column) {
        column.getAlias().ifPresent(alias -> mapColumn(alias, column));
        columns.add(column);
        return this;
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlColumn column) {
        return columnUntyped(column);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(SqlIdentifier column, Class<T> clazz) {
        return column(column);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return column(column, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return column(tableAlias, column, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(String columnName, Class<T> clazz) {
        return column(columnName);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(String tableAlias, String columnName, Class<T> clazz) {
        return column(tableAlias, columnName);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(tableAlias, columnName, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String columnSql, Class<T> clazz) {
        return columnDirect(columnSql);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String sql, String alias, Class<T> clazz) {
        return columnDirect(sql, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        return columnDirect(sql, alias, binds);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        return columnDirect(sql, alias, binds);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, Class<T> clazz) {
        return columnSql(columnSql);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, String alias, Class<T> clazz) {
        return columnSql(columnSql, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, String alias, Class<T> clazz, BindVariable... binds) {
        return columnSql(columnSql, alias, binds);
    }

    @Nonnull
    @Override
    public <T> SelectBuilder columnSql(String columnSql, String alias, Iterable<BindVariable> binds, Class<T> clazz) {
        return columnSql(columnSql, alias, binds);
    }

    @Nonnull
    @Override
    public SelectBuilder copy() {
        return new SelectBuilderImpl(sql, columns, tables, conditions);
    }
}
