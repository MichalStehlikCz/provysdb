package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatement;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderBaseImpl;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("ClassReferencesSubclass")
abstract class DbSelectBuilderBaseImpl<D extends DbSelectBuilderBaseImpl<D, S>, S extends SelectBuilderBaseImpl<S, DbSql>> {

  private final S selectBuilder;

  /**
   * Constructor that will create new DbSelectBuilderBase as wrapper around supplied select builder.
   * Given that it does not copy supplied builder, this constructor should not be published and
   * should be used with care
   *
   * @param selectBuilder is select builder this db select builder will delegate to
   */
  DbSelectBuilderBaseImpl(S selectBuilder) {
    this.selectBuilder = selectBuilder;
  }

  S getSelectBuilder() {
    return selectBuilder;
  }

  protected abstract D self();

  public List<SqlColumn> getColumns() {
    return selectBuilder.getColumns();
  }

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

  public DbSelectBuilderImpl column(SqlIdentifier column) {
    return new DbSelectBuilderImpl(selectBuilder.column(column));
  }

  public DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias) {
    return new DbSelectBuilderImpl(selectBuilder.column(column, alias));
  }

  public DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias) {
    return new DbSelectBuilderImpl(selectBuilder.column(tableAlias, column, alias));
  }

  public DbSelectBuilderImpl column(String columnName) {
    return new DbSelectBuilderImpl(selectBuilder.column(columnName));
  }

  public DbSelectBuilderImpl column(String tableAlias, String columnName) {
    return new DbSelectBuilderImpl(selectBuilder.column(tableAlias, columnName));
  }

  public DbSelectBuilderImpl column(String tableAlias, String columnName, String alias) {
    return new DbSelectBuilderImpl(selectBuilder.column(tableAlias, columnName, alias));
  }

  public DbSelectBuilderImpl column(Expression expression, SqlIdentifier alias) {
    return new DbSelectBuilderImpl(selectBuilder.column(expression, alias));
  }

  public DbSelectBuilderImpl column(Expression expression, String alias) {
    return new DbSelectBuilderImpl(selectBuilder.column(expression, alias));
  }

  public DbSelectBuilderImpl columnDirect(String columnSql) {
    return new DbSelectBuilderImpl(selectBuilder.columnDirect(columnSql));
  }

  public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias) {
    return new DbSelectBuilderImpl(selectBuilder.columnDirect(sqlColumn, alias));
  }

  public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias, BindName... binds) {
    return new DbSelectBuilderImpl(selectBuilder.columnDirect(sqlColumn, alias, binds));
  }

  public DbSelectBuilderImpl columnDirect(String sqlColumn, String alias,
      Collection<? extends BindName> binds) {
    return new DbSelectBuilderImpl(selectBuilder.columnDirect(sqlColumn, alias, binds));
  }

  public DbSelectBuilderImpl columnSql(String columnSql) {
    return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql));
  }

  public DbSelectBuilderImpl columnSql(String columnSql, String alias) {
    return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql, alias));
  }

  public DbSelectBuilderImpl columnSql(String columnSql, String alias, BindValue... binds) {
    return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql, alias, binds));
  }

  public DbSelectBuilderImpl columnSql(String columnSql, String alias,
      Collection<? extends BindValue> binds) {
    return new DbSelectBuilderImpl(selectBuilder.columnSql(columnSql, alias, binds));
  }

  public D from(SqlFrom table) {
    selectBuilder.from(table);
    return self();
  }

  public D from(SqlIdentifier tableName, SqlTableAlias alias) {
    selectBuilder.from(tableName, alias);
    return self();
  }

  public D from(String tableName, String alias) {
    selectBuilder.from(tableName, alias);
    return self();
  }

  public D fromDirect(String sqlSelect, SqlTableAlias alias) {
    selectBuilder.fromDirect(sqlSelect, alias);
    return self();
  }

  public D fromDirect(String sqlSelect, String alias) {
    selectBuilder.fromDirect(sqlSelect, alias);
    return self();
  }

  public D fromSql(String sqlSelect, SqlTableAlias alias) {
    selectBuilder.fromSql(sqlSelect, alias);
    return self();
  }

  public D fromSql(String sqlSelect, String alias) {
    selectBuilder.fromSql(sqlSelect, alias);
    return self();
  }

  public D from(Select select, SqlTableAlias alias) {
    selectBuilder.from(select, alias);
    return self();
  }

  public D from(Select select, String alias) {
    selectBuilder.from(select, alias);
    return self();
  }

  public D fromDual() {
    selectBuilder.fromDual();
    return self();
  }

  public D where(@Nullable Condition where) {
    selectBuilder.where(where);
    return self();
  }

  public D whereDirect(String conditionSql) {
    selectBuilder.whereDirect(conditionSql);
    return self();
  }

  public D whereDirect(String conditionSql, BindName... binds) {
    selectBuilder.whereDirect(conditionSql, binds);
    return self();
  }

  public D whereDirect(String conditionSql, Collection<? extends BindName> binds) {
    selectBuilder.whereDirect(conditionSql, binds);
    return self();
  }

  public D whereSql(String conditionSql) {
    selectBuilder.whereSql(conditionSql);
    return self();
  }

  public D whereSql(String conditionSql, BindValue... binds) {
    selectBuilder.whereSql(conditionSql, binds);
    return self();
  }

  public D whereSql(String conditionSql, Collection<? extends BindValue> binds) {
    selectBuilder.whereSql(conditionSql, binds);
    return self();
  }

  public D whereAnd(Condition... whereConditions) {
    selectBuilder.whereAnd(whereConditions);
    return self();
  }

  public D whereAnd(Collection<Condition> whereConditions) {
    selectBuilder.whereAnd(whereConditions);
    return self();
  }

  public D whereOr(Condition... whereConditions) {
    selectBuilder.whereOr(whereConditions);
    return self();
  }

  public D whereOr(Collection<Condition> whereConditions) {
    selectBuilder.whereOr(whereConditions);
    return self();
  }

  CodeBuilder builder() {
    return selectBuilder.builder();
  }

  public Select build() {
    return selectBuilder.build();
  }

  public SelectStatement prepare() {
    var codeBuilder = builder();
    return new SelectStatementImpl(codeBuilder.build(), codeBuilder.getBinds(), getSql());
  }

  @Override
  public String toString() {
    return "DbSelectBuilderBaseImpl{"
        + "selectBuilder=" + selectBuilder
        + '}';
  }
}
