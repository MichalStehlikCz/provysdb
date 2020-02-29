package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.Sql;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({"UnusedReturnValue", "ClassReferencesSubclass"})
public abstract class SelectBuilderBaseImpl<T extends SelectBuilderBaseImpl<T, S>, S extends Sql> {

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

  SelectBuilderBaseImpl(S sql, Collection<SqlColumn> columns, List<SqlFrom> tables,
      Collection<Condition> conditions) {
    this.sql = Objects.requireNonNull(sql);
    this.columnByName = columns.stream()
        .filter(column -> column.getAlias() != null)
        .collect(Collectors
            .toConcurrentMap(column -> column.getOptAlias().orElseThrow(), Function.identity()));
    this.tables = new ArrayList<>(tables);
    this.tableByAlias = tables.stream()
        .collect(Collectors.toConcurrentMap(SqlFrom::getAlias, Function.identity()));
    this.conditions = new ArrayList<>(conditions);
  }

  void mapColumn(SqlIdentifier alias, SqlColumn column) {
    if (columnByName.putIfAbsent(alias, column) != null) {
      throw new InternalException(
          "Attempt to insert duplicate column to column list (" + alias.getText() +
              " , " + this.toString() + ')');
    }
  }

  /**
   * Value of field sql.
   *
   * @return value of field sql
   */
  public S getSql() {
    return sql;
  }

  /**
   * Tables in from clause.
   *
   * @return value of field tables
   */
  public List<SqlFrom> getTables() {
    return Collections.unmodifiableList(tables);
  }

  /**
   * Conditions.
   *
   * @return value of field conditions
   */
  public List<Condition> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  abstract T self();

  /**
   * Alias of table that has been latest added.
   *
   * @return alias of table that has been added last, empty Optional if there are no tables.
   */
  Optional<SqlTableAlias> getLastTableAlias() {
    if (getTables().isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(tables.get(tables.size() - 1).getAlias());
  }

  public abstract List<SqlColumn> getColumns();

  List<SqlColumn> getModifiableColumns() {
    return new ArrayList<>(getColumns());
  }

  SelectBuilderImpl<S> columnUntyped(SqlColumn column) {
    var columns = getModifiableColumns();
    columns.add(column);
    return new SelectBuilderImpl<>(sql, columns, Collections.unmodifiableList(tables),
        Collections.unmodifiableList(conditions));
  }

  public SelectBuilderImpl<S> column(SqlIdentifier column) {
    return columnUntyped(sql.column(column));
  }

  public SelectBuilderImpl<S> column(SqlIdentifier column, SqlIdentifier alias) {
    return columnUntyped(sql.column(column, alias));
  }

  public SelectBuilderImpl<S> column(SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias) {
    return columnUntyped(sql.column(tableAlias, column, alias));
  }

  public SelectBuilderImpl<S> column(String columnName) {
    return getLastTableAlias()
        .map(tableAlias -> columnUntyped(sql.column(tableAlias, sql.name(columnName))))
        .orElseGet(() -> column(sql.name(columnName)));
  }

  public SelectBuilderImpl<S> column(String tableAlias, String columnName) {
    return columnUntyped(sql.column(tableAlias, columnName));
  }

  public SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias) {
    return columnUntyped(sql.column(tableAlias, columnName, alias));
  }

  public SelectBuilderImpl<S> column(Expression expression, SqlIdentifier alias) {
    return columnUntyped(sql.column(expression, alias));
  }

  public SelectBuilderImpl<S> column(Expression expression, String alias) {
    return columnUntyped(sql.column(expression, alias));
  }

  public SelectBuilderImpl<S> columnDirect(String columnSql) {
    return columnUntyped(sql.columnDirect(columnSql));
  }

  public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias) {
    return columnUntyped(sql.columnDirect(sqlColumn, alias));
  }

  public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias, BindName... binds) {
    return columnUntyped(sql.columnDirect(sqlColumn, alias, binds));
  }

  public SelectBuilderImpl<S> columnDirect(String sqlColumn, String alias, List<BindName> binds) {
    return columnUntyped(sql.columnDirect(sqlColumn, alias, binds));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql) {
    return columnUntyped(sql.columnSql(columnSql));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql, String alias) {
    return columnUntyped(sql.columnSql(columnSql, alias));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql, String alias, BindValue... binds) {
    return columnUntyped(sql.columnSql(columnSql, alias, binds));
  }

  public SelectBuilderImpl<S> columnSql(String columnSql, String alias, Collection<BindValue> binds) {
    return columnUntyped(sql.columnSql(columnSql, alias, binds));
  }

  private void mapTable(SqlTableAlias alias, SqlFrom table) {
    if (tableByAlias.putIfAbsent(alias, table) != null) {
      throw new InternalException(
          "Attempt to insert duplicate table to from list (" + alias.getAlias() +
              " , " + this.toString() + ")");
    }
  }

  public T from(SqlFrom table) {
    mapTable(table.getAlias(), table);
    tables.add(table);
    return self();
  }

  public T from(SqlIdentifier tableName, SqlTableAlias alias) {
    return from(sql.from(tableName, alias));
  }

  public T from(String tableName, String alias) {
    return from(sql.from(tableName, alias));
  }

  public T fromDirect(String sqlSelect, SqlTableAlias alias) {
    return from(sql.fromDirect(sqlSelect, alias));
  }

  public T fromDirect(String sqlSelect, String alias) {
    return from(sql.fromDirect(sqlSelect, alias));
  }

  public T fromSql(String sqlSelect, SqlTableAlias alias) {
    return from(sql.fromSql(sqlSelect, alias));
  }

  public T fromSql(String sqlSelect, String alias) {
    return from(sql.fromSql(sqlSelect, alias));
  }

  public T from(Select select, SqlTableAlias alias) {
    return from(sql.from(select, alias));
  }

  public T from(Select select, String alias) {
    return from(sql.from(select, alias));
  }

  public T fromDual() {
    return from(sql.fromDual());
  }

  public T where(@Nullable Condition where) {
    if ((where != null) && !where.isEmpty()) {
      conditions.add(where);
    }
    return self();
  }

  public T whereDirect(String conditionSql) {
    return where(sql.conditionDirect(conditionSql));
  }

  public T whereDirect(String conditionSql, BindName... binds) {
    return where(sql.conditionDirect(conditionSql, binds));
  }

  public T whereDirect(String conditionSql, List<BindName> binds) {
    return where(sql.conditionDirect(conditionSql, binds));
  }

  public T whereSql(String conditionSql) {
    return where(sql.conditionSql(conditionSql));
  }

  public T whereSql(String conditionSql, BindValue... binds) {
    return where(sql.conditionSql(conditionSql, binds));
  }

  public T whereSql(String conditionSql, Collection<BindValue> binds) {
    return where(sql.conditionSql(conditionSql, binds));
  }

  public T whereAnd(Condition... whereConditions) {
    return where(sql.conditionAnd(whereConditions));
  }

  public T whereAnd(Collection<Condition> whereConditions) {
    return where(sql.conditionAnd(whereConditions));
  }

  public T whereOr(Condition... whereConditions) {
    return where(sql.conditionOr(whereConditions));
  }

  public T whereOr(Collection<Condition> whereConditions) {
    return where(sql.conditionOr(whereConditions));
  }

  private static void addConditions(Iterable<? extends Condition> conditions, CodeBuilder builder) {
    for (var condition : conditions) {
      if ((condition instanceof ConditionJoined) &&
          ((ConditionJoined) condition).getOperator() == SqlConditionOperator.AND) {
        addConditions(((ConditionJoined) condition).getConditions(), builder);
      } else {
        condition.addSql(builder);
      }
    }
  }

  /**
   * Create copy of itself. Non-mutable objects can be returned as they are but mutable objects
   * (including this) has to be deep copied
   *
   * @return copy of itself
   */
  public abstract T copy();

  public CodeBuilder builder() {
    var builder = new CodeBuilderImpl()
        .appendLine("SELECT")
        .increasedIdent("", ", ", 4);
    for (var column : getColumns()) {
      column.addSql(builder);
      column.getOptAlias()
          .ifPresent(alias -> builder.append(' ').append(alias));
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

  public Select build() {
    var builder = builder();
    return new SelectImpl(builder.build(), builder.getBinds());
  }

  @Override
  public String toString() {
    return "SelectBuilderBaseImpl{"
        + "sql=" + sql
        + ", columnByName=" + columnByName
        + ", tables=" + tables
        + ", tableByAlias=" + tableByAlias
        + ", conditions=" + conditions
        + '}';
  }
}
