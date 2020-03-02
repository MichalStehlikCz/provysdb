package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.Condition;
import com.provys.provysdb.sqlbuilder.ExpressionT;
import com.provys.provysdb.sqlbuilder.SelectBuilderT0;
import com.provys.provysdb.sqlbuilder.Sql;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlFrom;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SelectBuilderT0Impl<S extends Sql> extends
    SelectBuilderBaseImpl<SelectBuilderT0Impl<S>, S>
    implements SelectBuilderT0 {

  public SelectBuilderT0Impl(S sql) {
    super(sql);
  }

  private SelectBuilderT0Impl(S sql, List<SqlFrom> tables, Collection<Condition> conditions) {
    super(sql, List.of(), tables, conditions);
  }

  @Override
  SelectBuilderT0Impl<S> self() {
    return this;
  }

  @Override
  public List<SqlColumn> getColumns() {
    return Collections.emptyList();
  }

  @Override
  protected List<SqlColumn> getModifiableColumns() {
    return new ArrayList<>(1);
  }

  @Override
  public SelectBuilderT0Impl<S> copy() {
    return new SelectBuilderT0Impl<>(getSql(), getTables(), getConditions());
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(SqlColumnT<T> column) {
    return new SelectBuilderT1Impl<>(getSql(), column, getTables(), getConditions());
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(SqlIdentifier column, Class<T> clazz) {
    return getLastTableAlias()
        .map(tableAlias -> column(getSql().column(tableAlias, column, clazz)))
        .orElseGet(() -> column(getSql().column(column, clazz)));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(SqlIdentifier column, SqlIdentifier alias,
      Class<T> clazz) {
    return getLastTableAlias()
        .map(tableAlias -> column(getSql().column(tableAlias, column, alias, clazz)))
        .orElseGet(() -> column(getSql().column(column, alias, clazz)));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, column, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(String columnName, Class<T> clazz) {
    return getLastTableAlias()
        .map(tableAlias -> column(getSql().column(tableAlias, getSql().name(columnName), clazz)))
        .orElseGet(() -> column(getSql().column(columnName, clazz)));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(String tableAlias, String columnName,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, columnName, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, columnName, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(ExpressionT<T> expression, SqlIdentifier alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> column(ExpressionT<T> expression, String alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, String alias,
      Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, String alias, Class<T> clazz,
      BindName... binds) {
    return column(getSql().columnDirect(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnDirect(String columnSql, String alias,
      List<? extends BindName> binds, Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, alias, binds, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, String alias, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, String alias, Class<T> clazz,
      BindValue... binds) {
    return column(getSql().columnSql(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> SelectBuilderT1Impl<S, T> columnSql(String columnSql, String alias,
      Collection<? extends BindValue> binds, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, alias, binds, clazz));
  }

  @Override
  public String toString() {
    return "SelectBuilderT0Impl{" + super.toString() + '}';
  }
}
