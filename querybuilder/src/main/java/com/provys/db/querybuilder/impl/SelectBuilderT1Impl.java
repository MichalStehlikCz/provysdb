package com.provys.db.querybuilder.impl;

import com.provys.db.querybuilder.BindValueBuilder;
import com.provys.db.querybuilder.Condition;
import com.provys.db.querybuilder.FromClause;
import com.provys.db.querybuilder.SelectExpressionBuilder;
import com.provys.db.querybuilder.Sql;
import com.provys.provysdb.sql.BindName;
import com.provys.db.querybuilder.SelectBuilderT1;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import com.provys.provysdb.sql.SimpleName;
import com.provys.db.querybuilder.QueryAlias;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SelectBuilderT1Impl<S extends Sql, T1> extends
    SelectBuilderBaseImpl<SelectBuilderT1Impl<S, T1>, S>
    implements SelectBuilderT1<T1> {

  private final SqlColumn<T1> column1;

  public SelectBuilderT1Impl(S sql, SqlColumn<T1> column1) {
    super(sql);
    this.column1 = Objects.requireNonNull(column1);
  }

  SelectBuilderT1Impl(S sql, SqlColumn<T1> column1, List<FromClause> tables,
      Collection<Condition> conditions) {
    super(sql, List.of(column1), tables, conditions);
    this.column1 = Objects.requireNonNull(column1);
  }

  @Override
  SelectBuilderT1Impl<S, T1> self() {
    return this;
  }

  @Override
  public List<SqlColumn> getColumns() {
    return List.of(column1);
  }

  /**
   * Definition of the first column.
   *
   * @return definition of first column
   */
  public SqlColumn<T1> getColumn1() {
    return column1;
  }

  @Override
  protected List<SqlColumn> getModifiableColumns() {
    return List.of(column1);
  }

  @Override
  public SelectBuilderT1Impl<S, T1> copy() {
    return new SelectBuilderT1Impl<>(getSql(), column1, getTables(), getConditions());
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(SqlColumn<T> column) {
    return new SelectBuilderT2Impl<>(getSql(), column1, column, getTables(), getConditions());
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(SimpleName column, Class<T> clazz) {
    if (getTables().isEmpty()) {
      return column(getSql().column(column, clazz));
    }
    return column(
        getSql().column(getTables().get(getTables().size() - 1).getAlias(), column, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(SimpleName column, SimpleName alias,
      Class<T> clazz) {
    if (getTables().isEmpty()) {
      return column(getSql().column(column, alias, clazz));
    }
    return column(
        getSql().column(getTables().get(getTables().size() - 1).getAlias(), column, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(QueryAlias tableAlias, SimpleName column,
      SimpleName alias,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, column, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(String columnName, Class<T> clazz) {
    if (getTables().isEmpty()) {
      return column(getSql().column(columnName, clazz));
    }
    return column(getSql()
        .column(getTables().get(getTables().size() - 1).getAlias(), getSql().name(columnName),
            clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(String tableAlias, String columnName,
      Class<T> clazz) {
    return column(getSql().column(tableAlias, columnName, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(String tableAlias, String columnName,
      String alias, Class<T> clazz) {
    return column(getSql().column(tableAlias, columnName, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(SelectExpressionBuilder<T> expression, SimpleName alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> column(SelectExpressionBuilder<T> expression, String alias) {
    return column(getSql().column(expression, alias));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnDirect(String columnSql, Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnDirect(String columnSql, String alias,
      Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnDirect(String columnSql, String alias,
      Class<T> clazz, BindName... binds) {
    return column(getSql().columnDirect(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnDirect(String columnSql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz) {
    return column(getSql().columnDirect(columnSql, alias, binds, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnSql(String columnSql, Class<T> clazz) {
    return column(getSql().columnSql(columnSql, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnSql(String columnSql, String alias,
      Class<T> clazz) {
    return column(getSql().columnSql(columnSql, alias, clazz));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnSql(String columnSql, String alias, Class<T> clazz,
      BindValueBuilder... binds) {
    return column(getSql().columnSql(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> SelectBuilderT2Impl<S, T1, T> columnSql(String columnSql, String alias,
      Collection<? extends BindValueBuilder> binds,
      Class<T> clazz) {
    return column(getSql().columnSql(columnSql, alias, binds, clazz));
  }

  @Override
  public String toString() {
    return "SelectBuilderT1Impl{"
        + "column1=" + column1
        + ", " + super.toString() + '}';
  }
}
