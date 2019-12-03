package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT0;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatement;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT0Impl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

class DbSelectBuilderT0Impl implements DbSelectBuilderT0 {

    private static final Logger LOG = LogManager.getLogger(DbSelectBuilderT0Impl.class);

    @Nonnull
    private final SelectBuilderT0Impl<DbSql> builder;

    DbSelectBuilderT0Impl(DbSql sql) {
        builder = new SelectBuilderT0Impl<>(sql);
    }

    private DbSelectBuilderT0Impl(SelectBuilderT0Impl<DbSql> builder) {
        this.builder = builder.copy();
    }

    @Override
    @Nonnull
    public List<SqlColumn> getColumns() {
        return builder.getColumns();
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl copy() {
        return new DbSelectBuilderT0Impl(builder.copy());
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlColumnT<T> column) {
        return new DbSelectBuilderT1Impl<>(builder.column(column));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlIdentifier column, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.column(column, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.column(column, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.column(tableAlias, column, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(String columnName, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.column(columnName, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.column(tableAlias, columnName, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.column(tableAlias, columnName, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.columnDirect(columnSql, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.columnDirect(columnSql, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, Class<T> clazz, BindName... binds) {
        return new DbSelectBuilderT1Impl<>(builder.columnDirect(columnSql, alias, clazz, binds));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnDirect(String columnSql, String alias, List<BindName> binds, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.columnDirect(columnSql, alias, binds, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.columnSql(columnSql, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.columnSql(columnSql, alias, clazz));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds) {
        return new DbSelectBuilderT1Impl<>(builder.columnSql(columnSql, alias, clazz, binds));
    }

    @Override
    @Nonnull
    public <T> DbSelectBuilderT1Impl<T> columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return new DbSelectBuilderT1Impl<>(builder.columnSql(columnSql, alias, binds, clazz));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlIdentifier column) {
        return new DbSelectBuilderImpl(builder.column(column));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias) {
        return new DbSelectBuilderImpl(builder.column(column, alias));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
        return new DbSelectBuilderImpl(builder.column(tableAlias, column, alias));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(String columnName) {
        return new DbSelectBuilderImpl(builder.column(columnName));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(String tableAlias, String columnName) {
        return new DbSelectBuilderImpl(builder.column(tableAlias, columnName));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl column(String tableAlias, String columnName, String alias) {
        return new DbSelectBuilderImpl(builder.column(tableAlias, columnName, alias));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String columnSql) {
        return new DbSelectBuilderImpl(builder.columnDirect(columnSql));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias) {
        return new DbSelectBuilderImpl(builder.columnDirect(sqlColumn, alias));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, BindName... binds) {
        return new DbSelectBuilderImpl(builder.columnDirect(sqlColumn, alias, binds));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, List<BindName> binds) {
        return new DbSelectBuilderImpl(builder.columnDirect(sqlColumn, alias, binds));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql) {
        return new DbSelectBuilderImpl(builder.columnSql(columnSql));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias) {
        return new DbSelectBuilderImpl(builder.columnSql(columnSql, alias));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias, BindValue... binds) {
        return new DbSelectBuilderImpl(builder.columnSql(columnSql, alias, binds));
    }

    @Override
    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias, Iterable<BindValue> binds) {
        return new DbSelectBuilderImpl(builder.columnSql(columnSql, alias, binds));
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl from(SqlFrom table) {
        builder.from(table);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl from(SqlIdentifier tableName, SqlTableAlias alias) {
        builder.from(tableName, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl from(String tableName, String alias) {
        builder.from(tableName, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl fromDirect(String sqlSelect, SqlTableAlias alias) {
        builder.fromDirect(sqlSelect, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl fromDirect(String sqlSelect, String alias) {
        builder.fromDirect(sqlSelect, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl fromSql(String sqlSelect, SqlTableAlias alias) {
        builder.fromSql(sqlSelect, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl fromSql(String sqlSelect, String alias) {
        builder.fromSql(sqlSelect, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl from(Select select, SqlTableAlias alias) {
        builder.from(select, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl from(Select select, String alias) {
        builder.from(select, alias);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl fromDual() {
        builder.fromDual();
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl where(Condition where) {
        builder.where(where);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereDirect(String conditionSql) {
        builder.whereDirect(conditionSql);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereDirect(String conditionSql, BindName... binds) {
        builder.whereDirect(conditionSql, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereDirect(String conditionSql, List<BindName> binds) {
        builder.whereDirect(conditionSql, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereSql(String conditionSql) {
        builder.whereSql(conditionSql);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereSql(String conditionSql, BindValue... binds) {
        builder.whereSql(conditionSql, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereSql(String conditionSql, Iterable<BindValue> binds) {
        builder.whereSql(conditionSql, binds);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereAnd(Condition... whereConditions) {
        builder.whereAnd(whereConditions);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereAnd(Collection<Condition> whereConditions) {
        builder.whereAnd(whereConditions);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereOr(Condition... whereConditions) {
        builder.whereOr(whereConditions);
        return this;
    }

    @Override
    @Nonnull
    public DbSelectBuilderT0Impl whereOr(Collection<Condition> whereConditions) {
        builder.whereOr(whereConditions);
        return this;
    }

    @Nonnull
    @Override
    public Select build() {
        throw new InternalException(LOG, "Cannot build select statement with no columns " + this);
    }

    @Override
    @Nonnull
    public SelectStatement prepare() {
        throw new InternalException(LOG, "Cannot build select statement with no columns " + this);
    }
}
