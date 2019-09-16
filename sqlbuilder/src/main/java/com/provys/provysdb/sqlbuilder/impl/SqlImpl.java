package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

public class SqlImpl implements Sql {

    @Nonnull
    @Override
    public SqlName name(String name) {
        return new SqlNameImpl(name);
    }

    @Nonnull
    @Override
    public SqlColumn column(SqlName column) {
        return column(null, column, null);
    }

    @Nonnull
    @Override
    public SqlColumn column(SqlName column, @Nullable SqlName alias) {
        return column(null, column, alias);
    }

    @Nonnull
    @Override
    public SqlColumn column(@Nullable SqlTableAlias tableAlias, SqlName column) {
        return new SqlColumnSimple(tableAlias, column, null);
    }

    @Nonnull
    @Override
    public SqlColumn column(@Nullable SqlTableAlias tableAlias, SqlName column, @Nullable SqlName alias) {
        return new SqlColumnSimple(tableAlias, column, alias);
    }

    @Nonnull
    @Override
    public SqlColumn column(@Nullable String tableAlias, String columnName, @Nullable String alias) {
        return column((tableAlias == null) ? null : tableAlias(tableAlias), name(columnName),
                (alias == null) ? null : name(alias));
    }

    @Nonnull
    @Override
    public SqlColumn column(String tableAlias, String columnName) {
        return column(tableAlias, columnName, null);
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql) {
        return columnSql(columnSql, null);
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql, @Nullable String alias) {
        return new SqlColumnSql(columnSql, (alias == null) ? null : name(alias));
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql, @Nullable String alias, BindVariable... binds) {
        return columnSql(columnSql, alias, Arrays.asList(binds));
    }

    @Nonnull
    @Override
    public SqlColumn columnSql(String columnSql, @Nullable String alias, Collection<BindVariable> binds) {
        return new SqlColumnSql(columnSql, (alias == null) ? null : name(alias), binds);
    }

    @Nonnull
    @Override
    public SqlTableAlias tableAlias(String tableAlias) {
        return new SqlTableAliasImpl(tableAlias);
    }

    @Nonnull
    @Override
    public SqlFrom from(SqlName tableName, SqlTableAlias alias) {
        return new SqlFromSimple(tableName, alias);
    }

    @Nonnull
    @Override
    public SqlFrom from(String tableName, String alias) {
        return from(name(tableName), tableAlias(alias));
    }

    @Nonnull
    @Override
    public SqlFrom fromSql(String sqlSelect, SqlTableAlias alias) {
        return new SqlFromSql(sqlSelect, alias);
    }

    @Nonnull
    @Override
    public SqlFrom fromSql(String sqlSelect, String alias) {
        return fromSql(sqlSelect, tableAlias(alias));
    }

    @Nonnull
    @Override
    public SqlFrom from(Select select, SqlTableAlias alias) {
        return new SqlFromSelect(select, alias);
    }

    @Nonnull
    @Override
    public SqlFrom from(Select select, String alias) {
        return new SqlFromSelect(select, tableAlias(alias));
    }

    @Nonnull
    @Override
    public SqlWhere whereSql(String conditionSql) {
        return new SqlWhereSimple(conditionSql);
    }

    @Nonnull
    @Override
    public SqlWhere whereSql(String conditionSql, BindVariable... bindVariable) {
        return whereSql(conditionSql, Arrays.asList(bindVariable));
    }

    @Nonnull
    @Override
    public SqlWhere whereSql(String conditionSql, Collection<BindVariable> binds) {
        return new SqlWhereSimpleWithBinds(conditionSql, binds);
    }

    @Nonnull
    @Override
    public SqlWhere whereAnd(SqlWhere... whereConditions) {
        return whereAnd(Arrays.asList(whereConditions));
    }

    @Nonnull
    @Override
    public SqlWhere whereAnd(Collection<SqlWhere> whereConditions) {
        // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
        return new SqlWhereJoinerImpl(SqlConditionOperator.AND, whereConditions).build();
    }

    @Nonnull
    @Override
    public SqlWhereJoiner whereAndJoiner() {
        return new SqlWhereJoinerImpl(SqlConditionOperator.AND);
    }

    @Nonnull
    @Override
    public SqlWhere whereOr(SqlWhere... whereConditions) {
        return whereOr(Arrays.asList(whereConditions));
    }

    @Nonnull
    @Override
    public SqlWhere whereOr(Collection<SqlWhere> whereConditions) {
        // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
        return new SqlWhereJoinerImpl(SqlConditionOperator.OR, whereConditions).build();
    }

    @Nonnull
    @Override
    public SqlWhereJoiner whereOrJoiner() {
        return new SqlWhereJoinerImpl(SqlConditionOperator.OR);
    }
}
