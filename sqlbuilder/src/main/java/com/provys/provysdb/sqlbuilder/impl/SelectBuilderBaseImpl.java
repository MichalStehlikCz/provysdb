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

@SuppressWarnings("UnusedReturnValue")
public abstract class SelectBuilderBaseImpl<T extends SelectBuilderBaseImpl<T, S>, S extends Sql> {

    private static final Logger LOG = LogManager.getLogger(SelectBuilderBaseImpl.class);

    private final S sql;
    private final Map<SqlIdentifier, SqlColumn> columnByName;
    private final List<SqlFrom> tables;
    private final Map<SqlTableAlias, SqlFrom> tableByAlias;
    private final List<Condition> conditions;

    SelectBuilderBaseImpl(S sql) {
        this.sql = Objects.requireNonNull(sql);
        columnByName = new ConcurrentHashMap<>(5);
        tables = new ArrayList<>(2);
        tableByAlias = new ConcurrentHashMap<>(2);
        conditions = new ArrayList<>(5);
    }

    SelectBuilderBaseImpl(S sql, List<SqlColumn> columns, List<SqlFrom> tables, Collection<Condition> conditions) {
        this.sql = Objects.requireNonNull(sql);
        this.columnByName = columns.stream().filter(column -> column.getAlias().isPresent()).collect(
                Collectors.toConcurrentMap(column -> column.getAlias().orElse(null), Function.identity()));
        this.tables = new ArrayList<>(tables);
        this.tableByAlias = tables.stream().collect(Collectors.toConcurrentMap(SqlFrom::getAlias, Function.identity()));
        this.conditions = new ArrayList<>(conditions);
    }

    protected void mapColumn(SqlIdentifier alias, SqlColumn column) {
        if (columnByName.putIfAbsent(alias, column) != null) {
            throw new InternalException(LOG, "Attempt to insert duplicate column to column list (" + alias.getText() +
                    " , " + this.toString() + ")");
        }
    }

    /**
     * @return value of field sql
     */
    public S getSql() {
        return sql;
    }

    /**
     * @return value of field tables
     */
    public List<SqlFrom> getTables() {
        return tables;
    }

    /**
     * @return value of field conditions
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    @Nonnull
    abstract T self();

    @Nonnull
    protected Optional<SqlTableAlias> getLastTableAlias() {
        if (getTables().isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tables.get(tables.size()-1).getAlias());
    }

    @Nonnull
    public abstract List<SqlColumn> getColumns();

    @Nonnull
    protected List<SqlColumn> getModifiableColumns() {
        return new ArrayList<>(getColumns());
    }

    @Nonnull
    protected SelectBuilderImpl<S> columnUntyped(SqlColumn column) {
        var columns = getModifiableColumns();
        columns.add(column);
        return new SelectBuilderImpl<>(sql, columns, Collections.unmodifiableList(tables),
                Collections.unmodifiableList(conditions));
    }

    @Nonnull
    public SelectBuilderImpl<S> column(SqlIdentifier column) {
        return columnUntyped(sql.column(column));
    }

    @Nonnull
    public SelectBuilderImpl<S> column(SqlIdentifier column, SqlIdentifier alias) {
        return columnUntyped(sql.column(column, alias));
    }

    @Nonnull
    public SelectBuilderImpl<S> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
        return columnUntyped(sql.column(tableAlias, column, alias));
    }

    @Nonnull
    public SelectBuilderImpl<S> column(String columnName) {
        return getLastTableAlias()
                .map(tableAlias -> columnUntyped(sql.column(tableAlias, sql.name(columnName))))
                .orElseGet(() -> column(sql.name(columnName)));
    }

    @Nonnull
    public SelectBuilderImpl<S> column(String tableAlias, String columnName) {
        return columnUntyped(sql.column(tableAlias, columnName));
    }

    @Nonnull
    public SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias) {
        return columnUntyped(sql.column(tableAlias, columnName, alias));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnDirect(String columnSql) {
        return columnUntyped(sql.columnDirect(columnSql));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias) {
        return columnUntyped(sql.columnDirect(sqlColumn, alias));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias, BindName... binds) {
        return columnUntyped(sql.columnDirect(sqlColumn, alias, binds));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias, List<BindName> binds) {
        return columnUntyped(sql.columnDirect(sqlColumn, alias, binds));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnSql(String columnSql) {
        return columnUntyped(sql.columnSql(columnSql));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnSql(String columnSql, String alias) {
        return columnUntyped(sql.columnSql(columnSql, alias));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnSql(String columnSql, String alias, BindValue... binds) {
        return columnUntyped(sql.columnSql(columnSql, alias, binds));
    }

    @Nonnull
    public SelectBuilderImpl<S> columnSql(String columnSql, String alias, Iterable<BindValue> binds) {
        return columnUntyped(sql.columnSql(columnSql, alias, binds));
    }

    private void mapTable(SqlTableAlias alias, SqlFrom table) {
        if (tableByAlias.putIfAbsent(alias, table) != null) {
            throw new InternalException(LOG, "Attempt to insert duplicate table to from list (" + alias.getAlias() +
                    " , " + this.toString() + ")");
        }
    }

    @Nonnull
    public T from(SqlFrom table) {
        mapTable(table.getAlias(), table);
        tables.add(table);
        return self();
    }

    @Nonnull
    public T from(SqlIdentifier tableName, SqlTableAlias alias) {
        return from(sql.from(tableName, alias));
    }

    @Nonnull
    public T from(String tableName, String alias) {
        return from(sql.from(tableName, alias));
    }

    @Nonnull
    public T fromDirect(String sqlSelect, SqlTableAlias alias) {
        return from(sql.fromDirect(sqlSelect, alias));
    }

    @Nonnull
    public T fromDirect(String sqlSelect, String alias) {
        return from(sql.fromDirect(sqlSelect, alias));
    }

    @Nonnull
    public T fromSql(String sqlSelect, SqlTableAlias alias) {
        return from(sql.fromSql(sqlSelect, alias));
    }

    @Nonnull
    public T fromSql(String sqlSelect, String alias) {
        return from(sql.fromSql(sqlSelect, alias));
    }

    @Nonnull
    public T from(Select select, SqlTableAlias alias) {
        return from(sql.from(select, alias));
    }

    @Nonnull
    public T from(Select select, String alias) {
        return from(sql.from(select, alias));
    }

    @Nonnull
    public T fromDual() {
        return from(sql.fromDual());
    }

    @Nonnull
    public T where(Condition where) {
        if (!where.isEmpty()) {
            conditions.add(where);
        }
        return self();
    }

    @Nonnull
    public T whereDirect(String conditionSql) {
        return where(sql.conditionDirect(conditionSql));
    }

    @Nonnull
    public T whereDirect(String conditionSql, BindName... binds) {
        return where(sql.conditionDirect(conditionSql, binds));
    }

    @Nonnull
    public T whereDirect(String conditionSql, List<BindName> binds) {
        return where(sql.conditionDirect(conditionSql, binds));
    }

    @Nonnull
    public T whereSql(String conditionSql) {
        return where(sql.conditionSql(conditionSql));
    }

    @Nonnull
    public T whereSql(String conditionSql, BindValue... binds) {
        return where(sql.conditionSql(conditionSql, binds));
    }

    @Nonnull
    public T whereSql(String conditionSql, Iterable<BindValue> binds) {
        return where(sql.conditionSql(conditionSql, binds));
    }

    @Nonnull
    public T whereAnd(Condition... whereConditions) {
        return where(sql.conditionAnd(whereConditions));
    }

    @Nonnull
    public T whereAnd(Collection<Condition> whereConditions) {
        return where(sql.conditionAnd(whereConditions));
    }

    @Nonnull
    public T whereOr(Condition... whereConditions) {
        return where(sql.conditionOr(whereConditions));
    }

    @Nonnull
    public T whereOr(Collection<Condition> whereConditions) {
        return where(sql.conditionOr(whereConditions));
    }

    private void addConditions(Collection<Condition> conditions, CodeBuilder builder) {
        for (var condition : conditions) {
            if ((condition instanceof ConditionJoined) &&
                    ((ConditionJoined) condition).getOperator().equals(SqlConditionOperator.AND)) {
                addConditions(((ConditionJoined) condition).getConditions(), builder);
            } else {
                condition.addSql(builder);
            }
        }
    }

    /**
     * Create copy of itself. Non-mutable objects can be returned as they are but mutable objects (including this) has
     * to be deep copied
     *
     * @return copy of itself
     */
    @Nonnull
    public abstract T copy();

    @Nonnull
    public CodeBuilder builder() {
        var builder = new CodeBuilderImpl()
                .appendLine("SELECT")
                .increasedIdent("", ", ", 4);
        for (var column : getColumns()) {
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
        return builder;
    }

    @Nonnull
    public Select build() {
        var builder = builder();
        return new SelectImpl(builder.build(), builder.getBinds());
    }
}
