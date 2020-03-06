package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT2;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import com.provys.provysdb.sqlbuilder.impl.SelectBuilderT2Impl;
import java.util.Collection;

class DbSelectBuilderT2Impl<T1, T2>
    extends
    DbSelectBuilderBaseImpl<DbSelectBuilderT2Impl<T1, T2>, SelectBuilderT2Impl<DbSql, T1, T2>>
    implements DbSelectBuilderT2<T1, T2> {

  DbSelectBuilderT2Impl(DbSql sql, SqlColumnT<T1> column1, SqlColumnT<T2> column2) {
    super(new SelectBuilderT2Impl<>(sql, column1, column2));
  }

  DbSelectBuilderT2Impl(SelectBuilderT2Impl<DbSql, T1, T2> selectBuilder) {
    super(selectBuilder);
  }

  @Override
  protected DbSelectBuilderT2Impl<T1, T2> self() {
    return this;
  }

  @Override
  public DbSelectBuilderT2Impl<T1, T2> copy() {
    return new DbSelectBuilderT2Impl<>(getSelectBuilder().copy());
  }

  @Override
  public <T> DbSelectBuilderImpl column(SqlColumnT<T> column) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(column));
  }

  @Override
  public <T> DbSelectBuilderImpl column(SqlIdentifier column, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(column, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(column, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl column(SqlTableAlias tableAlias, SqlIdentifier column,
      SqlIdentifier alias, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(tableAlias, column, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl column(String columnName, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(columnName, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl column(String tableAlias, String columnName, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(tableAlias, columnName, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().column(tableAlias, columnName, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl columnDirect(String columnSql, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl columnDirect(String columnSql, String alias, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl columnDirect(String columnSql, String alias, Class<T> clazz,
      BindName... binds) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> DbSelectBuilderImpl columnDirect(String columnSql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnDirect(columnSql, alias, binds, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl columnSql(String columnSql, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, alias, clazz));
  }

  @Override
  public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias, Class<T> clazz,
      BindValue... binds) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, alias, clazz, binds));
  }

  @Override
  public <T> DbSelectBuilderImpl columnSql(String columnSql, String alias,
      Collection<? extends BindValue> binds, Class<T> clazz) {
    return new DbSelectBuilderImpl(getSelectBuilder().columnSql(columnSql, alias, binds, clazz));
  }

  @Override
  public String toString() {
    return "DbSelectBuilderT2Impl{" + super.toString() + '}';
  }
}
