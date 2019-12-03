package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSelectBuilder;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatement;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderImpl;

import javax.annotation.Nonnull;
import java.util.List;

class DbSelectBuilderImpl extends DbSelectBuilderBaseImpl<DbSelectBuilderImpl, SelectBuilderImpl<DbSql>>
        implements DbSelectBuilder {

    DbSelectBuilderImpl(DbSql sql) {
        super(new SelectBuilderImpl<>(sql));
    }

    DbSelectBuilderImpl(SelectBuilderImpl<DbSql> selectBuilder) {
        super(selectBuilder);
    }

    @Override
    protected DbSelectBuilderImpl self() {
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlColumn column) {
        getSelectBuilder().column(column);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl column(SqlIdentifier column, Class<T> clazz) {
        getSelectBuilder().column(column, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        getSelectBuilder().column(column, alias, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        getSelectBuilder().column(tableAlias, column, alias, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl column(String columnName, Class<T> clazz) {
        getSelectBuilder().column(columnName, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl column(String tableAlias, String columnName, Class<T> clazz) {
        getSelectBuilder().column(tableAlias, columnName, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        getSelectBuilder().column(tableAlias, columnName, alias, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnDirect(String columnSql, Class<T> clazz) {
        getSelectBuilder().columnDirect(columnSql, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnDirect(String sql, String alias, Class<T> clazz) {
        getSelectBuilder().columnDirect(sql, alias, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        getSelectBuilder().columnDirect(sql, alias, clazz, binds);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        getSelectBuilder().columnDirect(sql, alias, binds, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnSql(String columnSql, Class<T> clazz) {
        getSelectBuilder().columnSql(columnSql, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Class<T> clazz) {
        getSelectBuilder().columnSql(columnSql, alias, clazz);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        getSelectBuilder().columnSql(columnSql, alias, clazz, binds);
        return this;
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        getSelectBuilder().columnSql(columnSql, alias, binds, clazz);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl copy() {
        return new DbSelectBuilderImpl(getSelectBuilder().copy());
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlIdentifier column) {
        getSelectBuilder().column(column);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias) {
        getSelectBuilder().column(column, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
        getSelectBuilder().column(tableAlias, column, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(String columnName) {
        getSelectBuilder().column(columnName);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(String tableAlias, String columnName) {
        getSelectBuilder().column(tableAlias, columnName);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(String tableAlias, String columnName, String alias) {
        getSelectBuilder().column(tableAlias, columnName, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String columnSql) {
        getSelectBuilder().columnDirect(columnSql);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias) {
        getSelectBuilder().columnDirect(sqlColumn, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, BindName... binds) {
        getSelectBuilder().columnDirect(sqlColumn, alias, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, List<BindName> binds) {
        getSelectBuilder().columnDirect(sqlColumn, alias, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql) {
        getSelectBuilder().columnSql(columnSql);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias) {
        getSelectBuilder().columnSql(columnSql, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias, BindValue... binds) {
        getSelectBuilder().columnSql(columnSql, alias, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias, Iterable<BindValue> binds) {
        getSelectBuilder().columnSql(columnSql, alias, binds);
        return this;
    }
}
