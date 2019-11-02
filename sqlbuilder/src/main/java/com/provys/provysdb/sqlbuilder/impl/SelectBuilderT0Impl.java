package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
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

    private <T> SelectBuilderT1<T> column(SqlColumnT<T> column) {
        return new SelectBuilderT1Impl<>(sql, column, tables, conditions);
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(SqlIdentifier column, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(String columnName, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String columnSql, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String sql, String alias, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, String alias, Class<T> clazz, BindVariable... binds) {
        return null;
    }

    @Nonnull
    @Override
    public <T> SelectBuilderT1<T> columnSql(String columnSql, String alias, Iterable<BindVariable> binds, Class<T> clazz) {
        return null;
    }
}
