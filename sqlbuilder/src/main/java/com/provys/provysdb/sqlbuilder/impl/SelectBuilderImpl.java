package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class SelectBuilderImpl implements SelectBuilder {

    private static final Logger LOG = LogManager.getLogger(SelectBuilderImpl.class);

    private final Sql sql;
    private final List<SqlColumn> columns;
    private final Map<SqlIdentifier, SqlColumn> columnByName;
    private final List<SqlFrom> tables;
    private final Map<SqlTableAlias, SqlFrom> tableByAlias;
    private final List<SqlWhere> conditions;
    private final Map<String, BindVariable> bindByName;

    SelectBuilderImpl(Sql sql) {
        this.sql = Objects.requireNonNull(sql);
        columns = new ArrayList<>(5);
        columnByName = new ConcurrentHashMap<>(5);
        tables = new ArrayList<>(2);
        tableByAlias = new ConcurrentHashMap<>(2);
        conditions = new ArrayList<>(5);
        bindByName = new ConcurrentHashMap<>(5);
    }

    private SelectBuilderImpl(Sql sql, Collection<SqlColumn> columns, Collection<SqlFrom> tables,
                      Collection<SqlWhere> conditions, Collection<BindVariable> binds) {
        this.sql = sql;
        this.columns = new ArrayList<>(columns);
        this.columnByName = columns.stream().filter(column -> column.getAlias().isPresent()).collect(
                Collectors.toConcurrentMap(column -> column.getAlias().orElse(null), Function.identity()));
        this.tables = new ArrayList<>(tables);
        this.tableByAlias = tables.stream().collect(Collectors.toConcurrentMap(SqlFrom::getAlias, Function.identity()));
        this.conditions = new ArrayList<>(conditions);
        this.bindByName = binds.stream().collect(Collectors.toConcurrentMap(BindVariable::getName, Function.identity(),
                BindVariable::combine));
    }

    private void mapColumn(SqlIdentifier alias, SqlColumn column) {
        if (columnByName.putIfAbsent(alias, column) != null) {
            throw new InternalException(LOG, "Attempt to insert duplicate column to column list (" + alias.getText() +
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
    public SelectBuilder column(SqlIdentifier column) {
        return column(sql.column(column));
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlIdentifier column, SqlIdentifier alias) {
        return column(sql.column(column, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
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
            throw new InternalException(LOG, "Attempt to insert duplicate table to from list (" + alias.getAlias() +
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
    public SelectBuilder from(SqlIdentifier tableName, SqlTableAlias alias) {
        return from(sql.from(tableName, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder from(String tableName, String alias) {
        return from(sql.from(tableName, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder fromSql(String sqlSelect, SqlTableAlias alias) {
        return from(sql.fromSql(sqlSelect, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder fromSql(String sqlSelect, String alias) {
        return from(sql.fromSql(sqlSelect, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder from(Select select, SqlTableAlias alias) {
        return from(sql.from(select, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder from(Select select, String alias) {
        return from(sql.from(select, alias));
    }

    @Nonnull
    @Override
    public SelectBuilder fromDual() {
        return from(sql.fromDual());
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(SqlWhere where) {
        if (!where.isEmpty()) {
            conditions.add(where);
        }
        return this;
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(String conditionSql) {
        return whereSql(sql.whereSql(conditionSql));
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(String conditionSql, BindVariable... binds) {
        return whereSql(conditionSql, Arrays.asList(binds));
    }

    @Nonnull
    @Override
    public SelectBuilder whereSql(String conditionSql, Collection<BindVariable> binds) {
        return whereSql(sql.whereSql(conditionSql, binds));
    }

    @Nonnull
    @Override
    public SelectBuilder whereAnd(SqlWhere... whereConditions) {
        return whereSql(sql.whereAnd(whereConditions));
    }

    @Nonnull
    @Override
    public SelectBuilder whereAnd(Collection<SqlWhere> whereConditions) {
        return whereSql(sql.whereAnd(whereConditions));
    }

    @Nonnull
    @Override
    public SelectBuilder whereOr(SqlWhere... whereConditions) {
        return whereSql(sql.whereOr(whereConditions));
    }

    @Nonnull
    @Override
    public SelectBuilder whereOr(Collection<SqlWhere> whereConditions) {
        return whereSql(sql.whereOr(whereConditions));
    }

    @Nonnull
    @Override
    public SelectBuilder addBind(BindVariable bind) {
        bindByName.compute(bind.getName(), (key, oldBind) -> (oldBind==null) ? bind : bind.combine(oldBind));
        return this;
    }

    @Nonnull
    @Override
    public SelectBuilder addBinds(Collection<BindVariable> binds) {
        for (var bind : binds) {
            addBind(bind);
        }
        return this;
    }

    private void addConditions(Collection<SqlWhere> conditions, CodeBuilder builder) {
        for (var condition : conditions) {
            if ((condition instanceof SqlWhereJoined) && ((SqlWhereJoined) condition).getOperator().equals(SqlConditionOperator.AND)) {
                addConditions(((SqlWhereJoined) condition).getConditions(), builder);
            } else {
                condition.addSql(builder);
            }
        }
    }

    @Nonnull
    @Override
    public Select build() {
        var builder = new CodeBuilderImpl()
                .appendLine("SELECT")
                .increasedIdent("", ", ", 4);
        for (var column : columns) {
            column.addSql(builder);
            builder.appendLine();
        }
        builder.popIdent()
                .appendLine("FROM")
                .increasedIdent("", ", ", 4);
        for (var table : tables) {
            table.addSql(builder);
            builder.appendLine();
        }
        builder.popIdent();
        if (!conditions.isEmpty()) {
            builder.appendLine("WHERE")
                    .increasedIdent("", "AND ", 6);
            addConditions(conditions, builder);
            builder.popIdent();
        }
        return new SelectImpl(builder.build(), Collections.unmodifiableCollection(bindByName.values()));
    }

    @Nonnull
    @Override
    public SelectBuilder copy() {
        return new SelectBuilderImpl(sql, columns, tables, conditions, bindByName.values());
    }
}
