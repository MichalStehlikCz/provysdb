package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.SelectBuilder;
import com.provys.provysdb.sqlbuilder.Sql;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SelectBuilderImpl<S extends Sql> extends
    SelectBuilderBaseImpl<SelectBuilderImpl<S>, S> implements SelectBuilder {

  private final List<SqlColumn> columns;

  public SelectBuilderImpl(S sql) {
    super(sql);
    columns = new ArrayList<>(5);
  }

  SelectBuilderImpl(S sql, List<SqlColumn> columns, List<SqlFrom> tables,
      Collection<Condition> conditions) {
    super(sql, columns, tables, conditions);
    this.columns = new ArrayList<>(columns);
  }

  @Override
  SelectBuilderImpl<S> self() {
    return this;
  }

  @Override
  public List<SqlColumn> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  @Override
  SelectBuilderImpl<S> columnUntyped(SqlColumn column) {
    column.getOptAlias()
        .ifPresent(alias -> mapColumn(alias, column));
    columns.add(column);
    return this;
  }

  @Override
  public SelectBuilderImpl<S> column(SqlColumn column) {
    return columnUntyped(column);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SqlIdentifier column, Class<T> clazz) {
    return column(column);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SqlIdentifier column, SqlIdentifier alias,
      Class<T> clazz) {
    return column(column, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias, Class<T> clazz) {
    return column(tableAlias, column, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String columnName, Class<T> clazz) {
    return column(columnName);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, Class<T> clazz) {
    return column(tableAlias, columnName);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return column(tableAlias, columnName, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> column(Expression<T> expression, SqlIdentifier alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderImpl<S> column(Expression<T> expression, String alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String columnSql, Class<T> clazz) {
    return columnDirect(columnSql);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, Class<T> clazz) {
    return columnDirect(sql, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias, Class<T> clazz,
      BindName... binds) {
    return columnDirect(sql, alias, binds);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnDirect(String sql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz) {
    return columnDirect(sql, alias, binds);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, Class<T> clazz) {
    return columnSql(columnSql);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz) {
    return columnSql(columnSql, alias);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias, Class<T> clazz,
      BindValue... binds) {
    return columnSql(columnSql, alias, binds);
  }

  @Override
  public <T> SelectBuilderImpl<S> columnSql(String columnSql, String alias,
      Collection<? extends BindValue> binds, Class<T> clazz) {
    return columnSql(columnSql, alias, binds);
  }

  @Override
  public SelectBuilderImpl<S> copy() {
    return new SelectBuilderImpl<>(getSql(), columns, getTables(), getConditions());
  }

  @Override
  public String toString() {
    return "SelectBuilderImpl{"
        + "columns=" + columns
        + ", " + super.toString() + '}';
  }
}
