package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SelectBuilderT0Impl<S extends Sql> extends SelectBuilderBaseImpl<SelectBuilderT0Impl<S>, S>
        implements SelectBuilderT0 {

    public SelectBuilderT0Impl(S sql) {
        super(sql);
    }

    private SelectBuilderT0Impl(S sql, List<SqlFrom> tables, Collection<Condition> conditions) {
        super(sql, List.of(), tables, conditions);
    }

    @Nonnull
    @Override
    SelectBuilderT0Impl<S> self() {
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
    public SelectBuilderT0Impl<S> copy() {
        return new SelectBuilderT0Impl<>(getSql(), getTables(), getConditions());
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(SqlColumnT<T> column) {
        return new SelectBuilderT1Impl<>(getSql(), column, getTables(), getConditions());
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(SqlIdentifier column, Class<T> clazz) {
        return getLastTableAlias()
                .map(tableAlias -> column(getSql().column(tableAlias, column, clazz)))
                .orElseGet(() -> column(getSql().column(column, clazz)));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return getLastTableAlias()
                .map(tableAlias -> column(getSql().column(tableAlias, column, alias, clazz)))
                .orElseGet(() -> column(getSql().column(column, alias, clazz)));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias,
                                         Class<T> clazz) {
        return column(getSql().column(tableAlias, column, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(String columnName, Class<T> clazz) {
        return getLastTableAlias()
                .map(tableAlias -> column(getSql().column(tableAlias, getSql().name(columnName), clazz)))
                .orElseGet(() -> column(getSql().column(columnName, clazz)));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(String tableAlias, String columnName, Class<T> clazz) {
        return column(getSql().column(tableAlias, columnName, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return column(getSql().column(tableAlias, columnName, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(ExpressionT<T> expression, SqlIdentifier alias) {
        return column(getSql().column(expression, alias));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> column(ExpressionT<T> expression, String alias) {
        return column(getSql().column(expression, alias));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, Class<T> clazz) {
        return column(getSql().columnDirect(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return column(getSql().columnDirect(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return column(getSql().columnDirect(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return column(getSql().columnDirect(columnSql, alias, binds, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, Class<T> clazz) {
        return column(getSql().columnSql(columnSql, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return column(getSql().columnSql(columnSql, alias, clazz));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return column(getSql().columnSql(columnSql, alias, clazz, binds));
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return column(getSql().columnSql(columnSql, alias, binds, clazz));
    }
}
