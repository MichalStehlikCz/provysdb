package com.provys.db.querybuilder.elements;

import com.provys.db.querybuilder.Condition;
import com.provys.db.querybuilder.ConditionJoiner;
import com.provys.db.querybuilder.FromClause;
import com.provys.provysdb.sql.BindName;
import com.provys.db.sqlquery.queryold.name.BindNameImpl;
import com.provys.db.sqlquery.queryold.name.IdentifierImpl;
import com.provys.db.querybuilder.BindValueBuilder;
import com.provys.db.querybuilder.SelectExpressionBuilder;
import com.provys.provysdb.sql.Select;
import com.provys.db.querybuilder.Sql;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import com.provys.provysdb.sql.SimpleName;
import com.provys.db.querybuilder.QueryAlias;
import com.provys.db.querybuilder.TableColumn;
import sqlparser.SqlSymbol;
import java.util.Arrays;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ElementFactory implements Sql {

  @Override
  public BindName bind(String name) {
    return BindNameImpl.of(name);
  }

  @Override
  public <T> BindValueBuilder<T> bind(BindName bindName, @NonNull T value) {
    return BindValueBuilderImpl.ofObject(bindName.getName(), value);
  }

  @Override
  public <T> BindValueBuilder<T> bind(String name, @NonNull T value) {
    return BindValueBuilderImpl.ofObject(name, value);
  }

  @Override
  public <T> BindValueBuilder<T> bind(BindName bindName, @Nullable T value, Class<T> clazz) {
    return bind(bindName.getName(), value, clazz);
  }

  @Override
  public <T> BindValueBuilder<T> bind(String name, @Nullable T value, Class<T> clazz) {
    return BindValueBuilderImpl.ofType(name, value, clazz);
  }

  @Override
  public <T> BindValueBuilder<T> bindEmpty(String name, Class<T> clazz) {
    return BindValueBuilderImpl.ofType(name, null, clazz);
  }

  @Override
  public IdentifierImpl name(String name) {
    return IdentifierImpl.parse(name);
  }

  @Override
  public TableColumn column(SimpleName column) {
    return new SqlColumnSimple(null, column, null);
  }

  @Override
  public TableColumn column(SimpleName column, @Nullable SimpleName alias) {
    return new SqlColumnSimple(null, column, alias);
  }

  @Override
  public TableColumn column(@Nullable QueryAlias tableAlias, SimpleName column) {
    return new SqlColumnSimple(tableAlias, column, null);
  }

  @Override
  public TableColumn column(@Nullable QueryAlias tableAlias, SimpleName column,
      SimpleName alias) {
    return new SqlColumnSimple(tableAlias, column, alias);
  }

  @Override
  public TableColumn column(String columnName) {
    return new SqlColumnSimple(null, name(columnName), null);
  }

  @Override
  public TableColumn column(String tableAlias, String columnName) {
    return new SqlColumnSimple(tableAlias(tableAlias), name(columnName), null);
  }

  @Override
  public TableColumn column(String tableAlias, String columnName, String alias) {
    return column(tableAlias(tableAlias), name(columnName), name(alias));
  }

  @Override
  public SqlColumn column(SelectExpressionBuilder expression, SimpleName alias) {
    return new SqlColumnExpression(expression, alias);
  }

  @Override
  public SqlColumn column(SelectExpressionBuilder expression, String alias) {
    return column(expression, name(alias));
  }

  @Override
  public <T> SqlColumn<T> column(SelectExpressionBuilder<T> expression, @Nullable SimpleName alias) {
    return new SqlColumnExpression<>(expression, alias);
  }

  @Override
  public <T> SqlColumn<T> column(SelectExpressionBuilder<T> expression, String alias) {
    return column(expression, name(alias));
  }

  @Override
  public <T> SqlColumn<T> column(SqlColumn column, Class<T> clazz) {
    return new SqlColumnTypeWrapper<>(column, clazz);
  }

  @Override
  public <T> TableColumn<T> column(TableColumn column, Class<T> clazz) {
    return new SqlTableColumnTImpl<>(column, clazz);
  }

  @Override
  public <T> TableColumn<T> column(SimpleName column, Class<T> clazz) {
    return column(column(column), clazz);
  }

  @Override
  public <T> TableColumn<T> column(SimpleName column, SimpleName alias, Class<T> clazz) {
    return column(column(column, alias), clazz);
  }

  @Override
  public <T> TableColumn<T> column(QueryAlias tableAlias, SimpleName column,
      Class<T> clazz) {
    return column(column(tableAlias, column), clazz);
  }

  @Override
  public <T> TableColumn<T> column(QueryAlias tableAlias, SimpleName column,
      SimpleName alias, Class<T> clazz) {
    return column(column(tableAlias, column, alias), clazz);
  }

  @Override
  public <T> TableColumn<T> column(String columnName, Class<T> clazz) {
    return column(column(columnName), clazz);
  }

  @Override
  public <T> TableColumn<T> column(String tableAlias, String columnName, Class<T> clazz) {
    return column(column(tableAlias, columnName), clazz);
  }

  @Override
  public <T> TableColumn<T> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return column(column(tableAlias, columnName, alias), clazz);
  }

  @Override
  public SqlColumn columnDirect(String sql) {
    return new SqlColumnSql(sql, null);
  }

  @Override
  public SqlColumn columnDirect(String sql, String alias) {
    return new SqlColumnSql(sql, name(alias));
  }

  @Override
  public SqlColumn columnDirect(String sql, @Nullable String alias, BindName... binds) {
    return columnDirect(sql, alias, Arrays.asList(binds));
  }

  @Override
  public SqlColumn columnDirect(String sql, @Nullable String alias,
      Collection<? extends BindName> binds) {
    return new SqlColumnSql(sql, (alias == null) ? null : name(alias), binds);
  }

  @Override
  public <T> SqlColumn<T> columnDirect(String sql, Class<T> clazz) {
    return column(columnDirect(sql), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnDirect(String sql, String alias, Class<T> clazz) {
    return column(columnDirect(sql, alias), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnDirect(String sql, String alias, Class<T> clazz,
      BindName... binds) {
    return column(columnDirect(sql, alias, binds), clazz);
  }

  @Override
  public <T> SqlColumn<T> columnDirect(String sql, String alias,
      Collection<? extends BindName> binds,
      Class<T> clazz) {
    return column(columnDirect(sql, alias, binds), clazz);
  }

  @Override
  public QueryAlias tableAlias(String tableAlias) {
    return new QueryAliasImpl(tableAlias);
  }

  @Override
  public FromClause from(SimpleName tableName, QueryAlias alias) {
    return new SqlFromSimple(tableName, alias);
  }

  @Override
  public FromClause from(String tableName, String alias) {
    return from(name(tableName), tableAlias(alias));
  }

  @Override
  public FromClause from(Select select, QueryAlias alias) {
    return new SqlFromSelect(select, alias);
  }

  @Override
  public FromClause from(Select select, String alias) {
    return new SqlFromSelect(select, tableAlias(alias));
  }

  @Override
  public FromClause fromDirect(String sqlSelect, QueryAlias alias) {
    return new SqlFromSql(sqlSelect, alias);
  }

  @Override
  public FromClause fromDirect(String sqlSelect, String alias) {
    return fromDirect(sqlSelect, tableAlias(alias));
  }

  @Override
  public FromClause fromDual() {
    return SqlFromDual.getInstance();
  }

  @Override
  public Condition conditionDirect(String conditionSql) {
    return new ConditionSql(conditionSql);
  }

  @Override
  public Condition conditionDirect(String conditionSql, BindName... binds) {
    return conditionDirect(conditionSql, Arrays.asList(binds));
  }

  @Override
  public Condition conditionDirect(String conditionSql, Collection<? extends BindName> binds) {
    return new ConditionSimpleWithBinds(conditionSql, binds);
  }

  @Override
  public Condition conditionAnd(Condition... whereConditions) {
    return conditionAnd(Arrays.asList(whereConditions));
  }

  @Override
  public Condition conditionAnd(Collection<Condition> whereConditions) {
    // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
    return new ConditionJoinerImpl(ConditionOperator.AND, whereConditions).build();
  }

  @Override
  public ConditionJoiner conditionAndJoiner() {
    return new ConditionJoinerImpl(ConditionOperator.AND);
  }

  @Override
  public Condition conditionOr(Condition... whereConditions) {
    return conditionOr(Arrays.asList(whereConditions));
  }

  @Override
  public Condition conditionOr(Collection<Condition> whereConditions) {
    // joiner removes empty conditions and might remove lists when only 0 or 1 items are present
    return new ConditionJoinerImpl(ConditionOperator.OR, whereConditions).build();
  }

  @Override
  public ConditionJoiner conditionOrJoiner() {
    return new ConditionJoinerImpl(ConditionOperator.OR);
  }

  private static <T> Condition compare(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second,
      SqlSymbol comparison) {
    return new ConditionCompare<>(first, second, comparison);
  }

  @Override
  public <T> Condition eq(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return compare(first, second, SqlSymbol.EQUAL);
  }

  @Override
  public <T> Condition notEq(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return compare(first, second, SqlSymbol.NOT_EQUAL);
  }

  @Override
  public <T> Condition lessThan(SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return compare(first, second, SqlSymbol.LESS_THAN);
  }

  @Override
  public <T> Condition lessOrEqual(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return compare(first, second, SqlSymbol.LESS_OR_EQUAL);
  }

  @Override
  public <T> Condition greaterThan(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return compare(first, second, SqlSymbol.GRATER_THAN);
  }

  @Override
  public <T> Condition greaterOrEqual(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return compare(first, second, SqlSymbol.GREATER_OR_EQUAL);
  }

  @Override
  public <T> Condition isNull(SelectExpressionBuilder<T> first) {
    return new ConditionIsNull(first);
  }

  @Override
  public <T> SelectExpressionBuilder<T> nvl(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<? extends T> second) {
    return new FuncNvl<>(first, second);
  }

  @Override
  @SafeVarargs
  public final <T> SelectExpressionBuilder<T> coalesce(SelectExpressionBuilder<T> first,
      SelectExpressionBuilder<? extends T>... expressions) {
    return new FuncCoalesce<>(first, expressions);
  }

  @Override
  public String toString() {
    return "ElementFactory{}";
  }
}
