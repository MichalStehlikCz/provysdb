package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

    @Override
    public SqlColumn columnSql(String columnSql) {
        return null;
    }

    @Override
    public SqlColumn columnSql(String sql, String alias) {
        return null;
    }

    @Nonnull
    @Override
    public SqlTableAlias tableAlias(String tableAlias) {
        return new SqlTableAliasImpl(tableAlias);
    }

    @Override
    public SqlFrom from(String tableName, String alias) {
        return null;
    }

    @Override
    public SqlFrom fromSql(String sqlSelect, String alias) {
        return null;
    }

    @Override
    public SqlFrom from(Select sqlSelect, String alias) {
        return null;
    }

    @Override
    public SqlWhere where(SqlWhere condition) {
        return null;
    }

    @Override
    public SqlWhere whereSql(String conditionSql) {
        return null;
    }

    @Override
    public SqlWhere whereSql(String conditionSql, BindVariable... bindVariable) {
        return null;
    }

    @Override
    public SqlWhere whereAnd(SqlWhere... whereConditions) {
        return null;
    }

    @Override
    public SqlWhere whereAnd(Iterable<SqlWhere> whereConditions) {
        return null;
    }

    @Override
    public SqlWhereJoiner whereAndJoiner() {
        return null;
    }

    @Override
    public SqlWhere whereOr(SqlWhere... whereConditions) {
        return null;
    }

    @Override
    public SqlWhere whereOr(Iterable<SqlWhere> whereConditions) {
        return null;
    }

    @Override
    public SqlWhereJoiner whereOrJoiner() {
        return null;
    }
}
