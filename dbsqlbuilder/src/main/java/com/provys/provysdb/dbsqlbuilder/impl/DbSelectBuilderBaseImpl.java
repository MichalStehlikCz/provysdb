package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatement;
import com.provys.provysdb.sqlbuilder.*;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderBaseImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

abstract class DbSelectBuilderBaseImpl<D extends DbSelectBuilderBaseImpl<D, S>, S extends SelectBuilderBaseImpl<S, DbSql>> {

    @Nonnull
    private final S selectBuilder;

    /**
     * Constructor that will create new DbSelectBuilderBase as wrapper around supplied select builder. Given that it
     * does not copy supplied builder, this constructor should not be published and should be used with care
     *
     * @param selectBuilder is select builder this db select builder will delegate to
     */
    DbSelectBuilderBaseImpl(S selectBuilder) {
        this.selectBuilder = selectBuilder;
    }

    protected S getSelectBuilder() {
        return selectBuilder;
    }

    protected abstract D self();

    @Nonnull
    public List<SqlColumn> getColumns() {
        return selectBuilder.getColumns();
    }

    @Nonnull
    public abstract D copy();

    DbSql getSql() {
        return selectBuilder.getSql();
    }

    public List<SqlFrom> getTables() {
        return selectBuilder.getTables();
    }

    public List<Condition> getConditions() {
        return selectBuilder.getConditions();
    }

    @Nonnull
    public DbSelectBuilderImpl column(SqlIdentifier column) {
        return new DbSelectBuilderImpl(selectBuilder.column(column));
    }

    @Nonnull
    public DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias) {
        return new DbSelectBuilderImpl(selectBuilder.column(column, alias));
    }

    @Nonnull
    public DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
        return new DbSelectBuilderImpl(selectBuilder.column(tableAlias, column, alias));
    }

    @Nonnull
    public DbSelectBuilderImpl column(String columnName) {
        return new DbSelectBuilderImpl(selectBuilder.column(columnName));
    }

    @Nonnull
    public DbSelectBuilderImpl column(String tableAlias, String columnName) {
        return new DbSelectBuilderImpl(selectBuilder.column(tableAlias, columnName));
    }

    @Nonnull
    public DbSelectBuilderImpl column(String tableAlias, String columnName, String alias) {
        return new DbSelectBuilderImpl(selectBuilder.column(tableAlias, columnName, alias));
    }

    @Nonnull
    public DbSelectBuilderImpl columnDirect(String columnSql) {
        return new DbSelectBuilderImpl(selectBuilder.columnDirect(columnSql));
    }

    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias) {
        return new DbSelectBuilderImpl(selectBuilder.columnDirect(sqlColumn, alias));
    }

    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, BindName... binds) {
        return new DbSelectBuilderImpl(selectBuilder.columnDirect(sqlColumn, alias, binds));
    }

    @Nonnull
    public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, List<BindName> binds) {
        return new DbSelectBuilderImpl(selectBuilder.columnDirect(sqlColumn, alias, binds));
    }

    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql) {
        return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql));
    }

    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias) {
        return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql, alias));
    }

    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias, BindValue... binds) {
        return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql, alias, binds));
    }

    @Nonnull
    public DbSelectBuilderImpl columnSql(String columnSql, String alias, Iterable<BindValue> binds) {
        return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql, alias, binds));
    }

    @Nonnull
    public D from(SqlFrom table) {
        selectBuilder.from(table);
        return self();
    }

    @Nonnull
    public D from(SqlIdentifier tableName, SqlTableAlias alias) {
        selectBuilder.from(tableName, alias);
        return self();
    }

    @Nonnull
    public D from(String tableName, String alias) {
        selectBuilder.from(tableName, alias);
        return self();
    }

    @Nonnull
    public D fromDirect(String sqlSelect, SqlTableAlias alias) {
        selectBuilder.fromDirect(sqlSelect, alias);
        return self();
    }

    @Nonnull
    public D fromDirect(String sqlSelect, String alias) {
        selectBuilder.fromDirect(sqlSelect, alias);
        return self();
    }

    @Nonnull
    public D fromSql(String sqlSelect, SqlTableAlias alias) {
        selectBuilder.fromSql(sqlSelect, alias);
        return self();
    }

    @Nonnull
    public D fromSql(String sqlSelect, String alias) {
        selectBuilder.fromSql(sqlSelect, alias);
        return self();
    }

    @Nonnull
    public D from(Select select, SqlTableAlias alias) {
        selectBuilder.from(select, alias);
        return self();
    }

    @Nonnull
    public D from(Select select, String alias) {
        selectBuilder.from(select, alias);
        return self();
    }

    @Nonnull
    public D fromDual() {
        selectBuilder.fromDual();
        return self();
    }

    @Nonnull
    public D where(@Nullable Condition where) {
        selectBuilder.where(where);
        return self();
    }

    @Nonnull
    public D whereDirect(String conditionSql) {
        selectBuilder.whereDirect(conditionSql);
        return self();
    }

    @Nonnull
    public D whereDirect(String conditionSql, BindName... binds) {
        selectBuilder.whereDirect(conditionSql, binds);
        return self();
    }

    @Nonnull
    public D whereDirect(String conditionSql, List<BindName> binds) {
        selectBuilder.whereDirect(conditionSql, binds);
        return self();
    }

    @Nonnull
    public D whereSql(String conditionSql) {
        selectBuilder.whereSql(conditionSql);
        return self();
    }

    @Nonnull
    public D whereSql(String conditionSql, BindValue... binds) {
        selectBuilder.whereSql(conditionSql, binds);
        return self();
    }

    @Nonnull
    public D whereSql(String conditionSql, Iterable<BindValue> binds) {
        selectBuilder.whereSql(conditionSql, binds);
        return self();
    }

    @Nonnull
    public D whereAnd(Condition... whereConditions) {
        selectBuilder.whereAnd(whereConditions);
        return self();
    }

    @Nonnull
    public D whereAnd(Collection<Condition> whereConditions) {
        selectBuilder.whereAnd(whereConditions);
        return self();
    }

    @Nonnull
    public D whereOr(Condition... whereConditions) {
        selectBuilder.whereOr(whereConditions);
        return self();
    }

    @Nonnull
    public D whereOr(Collection<Condition> whereConditions) {
        selectBuilder.whereOr(whereConditions);
        return self();
    }

    @Nonnull
    public CodeBuilder builder() {
        return selectBuilder.builder();
    }

    @Nonnull
    public Select build() {
        return selectBuilder.build();
    }

    @Nonnull
    public SelectStatement prepare() {
        var codeBuilder = builder();
        return new SelectStatementImpl(codeBuilder.build(), codeBuilder.getBinds(), getSql());
    }
}
