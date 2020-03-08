package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT1;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.Identifier;
import com.provys.provysdb.sqlbuilder.QueryAlias;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT1Impl;
import java.util.Collection;

class DbSelectBuilderT1Impl<T1> extends
    DbSelectBuilderBaseImpl<DbSelectBuilderT1Impl<T1>, SelectBuilderT1Impl<DbSql, T1>>
    implements DbSelectBuilderT1<T1> {

  DbSelectBuilderT1Impl(DbSql sql, SqlColumn<T1> column) {
    super(new SelectBuilderT1Impl<>(sql, column));
  }

  DbSelectBuilderT1Impl(SelectBuilderT1Impl<DbSql, T1> selectBuilder) {
    super(selectBuilder);
  }

  @Override
  protected DbSelectBuilderT1Impl<T1> self() {
    return this;
  }

  @Override
  public DbSelectBuilderT1Impl<T1> copy() {
    return new DbSelectBuilderT1Impl<>(getSelectBuilder().copy());
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(SqlColumn<T> column) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(column));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(Identifier column, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(column, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(Identifier column, Identifier alias,
      Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(column, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(QueryAlias tableAlias, Identifier column,
      Identifier alias, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(tableAlias, column, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(String columnName, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(columnName, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(String tableAlias, String columnName,
      Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().column(tableAlias, columnName, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(
        getSelectBuilder().column(tableAlias, columnName, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnDirect(columnSql, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, String alias,
      Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnDirect(columnSql, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, String alias,
      Class<T> clazz, BindName... binds) {
    return new DbSelectBuilderT2Impl<>(
        getSelectBuilder().columnDirect(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnDirect(String columnSql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(
        getSelectBuilder().columnDirect(columnSql, alias, binds, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnSql(columnSql, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, String alias,
      Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().columnSql(columnSql, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, String alias, Class<T> clazz,
      BindValue... binds) {
    return new DbSelectBuilderT2Impl<>(
        getSelectBuilder().columnSql(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> DbSelectBuilderT2Impl<T1, T> columnSql(String columnSql, String alias,
      Collection<? extends BindValue> binds, Class<T> clazz) {
    return new DbSelectBuilderT2Impl<>(
        getSelectBuilder().columnSql(columnSql, alias, binds, clazz));
  }

  @Override
  public SelectStatementT1Impl<T1> prepare() {
    var builder = builder();
    return new SelectStatementT1Impl<T1>(builder.build(), builder.getBinds(), getSql(),
        getSelectBuilder().getColumn1());
  }

  @Override
  public String toString() {
    return "DbSelectBuilderT1Impl{" + super.toString() + '}';
  }
}
