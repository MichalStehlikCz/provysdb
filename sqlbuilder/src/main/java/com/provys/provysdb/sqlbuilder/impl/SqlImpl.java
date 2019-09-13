package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

public class SqlImpl implements Sql {

    @Override
    public SelectBuilder column(SqlColumn column) {
        return null;
    }

    @Override
    public SelectBuilder column(String columnName) {
        return null;
    }

    @Override
    public SelectBuilder columnSql(String columnSql) {
        return null;
    }

    @Override
    public SelectBuilder columnSql(String sql, String alias) {
        return null;
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
