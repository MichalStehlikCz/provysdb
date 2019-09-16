package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SelectBuilderImpl implements SelectBuilder {

    private static final Logger LOG = LogManager.getLogger(SelectBuilderImpl.class);

    private final Sql sql;
    private final List<SqlColumn> columns;
    private final Map<SqlName, SqlColumn> columnByName;
    private final List<SqlFrom> tables;
    private final Map<SqlTableAlias, SqlFrom> tableByAlias;
    private final List<SqlWhere> conditions;

    SelectBuilderImpl(Sql sql) {
        this.sql = sql;
        columns = new ArrayList<>(5);
        columnByName = new ConcurrentHashMap<>(5);
        tables = new ArrayList<>(2);
        tableByAlias = new ConcurrentHashMap<>(2);
        conditions = new ArrayList<>(5);
    }

    private void mapColumn(SqlName alias, SqlColumn column) {
        if (columnByName.putIfAbsent(alias, column) != null) {
            throw new InternalException(LOG, "Attempt to insert duplicate column to column list (" + alias.getName() +
                    " , " + this.toString() + ")");
        }
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlColumn column) {
        column.getAlias().ifPresent(alias -> mapColumn(alias, column));
        columns.add(column);
        return this;
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlName column) {
        return column(sql.column(column));
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlName column, SqlName alias) {
        return column(sql.column(column, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlTableAlias tableAlias, SqlName column, SqlName alias) {
        return column(sql.column(tableAlias, column, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder column(String columnName) {
        if (tables.isEmpty()) {
            throw new InternalException(LOG, "Column without table alias can only be added after table (" +
                    columnName + " , " + this.toString() + ")");
        }
        return column(sql.column(tables.get(tables.size() - 1).getAlias(), sql.name(columnName)));
    }

    @Nonnull
    @Override
    public SelectBuilder column(String tableAlias, String columnName, String alias) {
        return column(sql.column(tableAlias, columnName, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder column(String tableAlias, String columnName) {
        return column(sql.column(tableAlias, columnName));
    }

    @Nonnull
    @Override
    public SelectBuilder columnSql(String columnSql) {
        return column(sql.columnSql(columnSql));
    }

    @Nonnull
    @Override
    public SelectBuilder columnSql(String sqlColumn, String alias) {
        return column(sql.columnSql(sqlColumn, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder columnSql(String sqlColumn, String alias, BindVariable... binds) {
        return column(sql.columnSql(sqlColumn, alias, binds));
    }

    @Nonnull
    @Override
    public SelectBuilder columnSql(String sqlColumn, String alias, Collection<BindVariable> binds) {
        return column(sql.columnSql(sqlColumn, alias, binds));
    }

    private void mapTable(SqlTableAlias alias, SqlFrom table) {
        if (tableByAlias.putIfAbsent(alias, table) != null) {
            throw new InternalException(LOG, "Attempt to insert duplicate table to from list (" + alias.getAliasText() +
                    " , " + this.toString() + ")");
        }
    }

    @Nonnull
    @Override
    public SelectBuilder from(SqlFrom table) {
        mapTable(table.getAlias(), table);
        tables.add(table);
        return this;
    }

    @Nonnull
    @Override
    public SelectBuilder from(SqlName tableName, SqlTableAlias alias) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder from(String tableName, String alias) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder fromSql(String sqlSelect, SqlTableAlias alias) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder fromSql(String sqlSelect, String alias) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder from(Select select, SqlTableAlias alias) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder from(Select select, String alias) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(SqlWhere where) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(String conditionSql) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(String conditionSql, BindVariable... binds) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(String conditionSql, Collection<BindVariable> binds) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereAnd(SqlWhere... whereConditions) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereAnd(Collection<SqlWhere> whereConditions) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereOr(SqlWhere... whereConditions) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder whereOr(Collection<SqlWhere> whereConditions) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder addBind(BindVariable bind) {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder addBinds(Collection<BindVariable> binds) {
        return null;
    }

    @Nonnull
    @Override
    public Select build() {
        return null;
    }

    @Nonnull
    @Override
    public SelectBuilder copy() {
        return null;
    }
}
