package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.*;

public class SelectBuilderImpl<S extends Sql> extends SelectBuilderBaseImpl<SelectBuilderImpl<S>, S> implements SelectBuilder {

    private final List<SqlColumn> columns;

    public SelectBuilderImpl(S sql) {
        super(sql);
        columns = new ArrayList<>(5);
    }

    SelectBuilderImpl(S sql, List<SqlColumn> columns, List<SqlFrom> tables, Collection<Condition> conditions) {
        super(sql, columns, tables, conditions);
        this.columns = new ArrayList<>(columns);
    }

    @Nonnull
    @Override
    SelectBuilderImpl<S> self() {
        return this;
    }

    @Nonnull
    @Override
    public List<SqlColumn> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    @Nonnull
    @Override
    public SelectBuilderImpl<S> columnUntyped(SqlColumn column) {
        column.getAlias().ifPresent(alias -> mapColumn(alias, column));
        columns.add(column);
        return this;
    }

    @Nonnull
    @Override
    public SelectBuilderImpl<S> column(SqlColumn column) {
        return columnUntyped(column);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlIdentifier column, Class<T> clazz) {
        return column(column);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return column(column, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return column(tableAlias, column, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(String columnName, Class<T> clazz) {
        return column(columnName);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, Class<T> clazz) {
        return column(tableAlias, columnName);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(tableAlias, columnName, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String columnSql, Class<T> clazz) {
        return columnDirect(columnSql);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, Class<T> clazz) {
        return columnDirect(sql, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        return columnDirect(sql, alias, binds);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        return columnDirect(sql, alias, binds);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, Class<T> clazz) {
        return columnSql(columnSql);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz) {
        return columnSql(columnSql, alias);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return columnSql(columnSql, alias, binds);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return columnSql(columnSql, alias, binds);
    }

    @Nonnull
    @Override
    public SelectBuilderImpl<S> copy() {
        return new SelectBuilderImpl<>(getSql(), columns, getTables(), getConditions());
    }
}
