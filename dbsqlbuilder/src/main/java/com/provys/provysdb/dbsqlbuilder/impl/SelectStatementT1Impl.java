package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbRowMapper;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatementT1;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;

class SelectStatementT1Impl<T1> extends SelectStatementTImpl<SelectStatementT1Impl<T1>>
    implements SelectStatementT1<T1> {

  private final SqlColumnT<T1> column1;

  @SuppressWarnings("BoundedWildcard")
  SelectStatementT1Impl(String sqlText, Collection<? extends BindName> binds, DbSql sqlContext,
      SqlColumnT<T1> column1) {
    super(sqlText, binds, sqlContext);
    this.column1 = column1;
  }

  @SuppressWarnings("BoundedWildcard")
  SelectStatementT1Impl(Select select, DbSql sqlContext, SqlColumnT<T1> column1) {
    super(select, sqlContext);
    this.column1 = column1;
  }

  @SuppressWarnings("BoundedWildcard")
  SelectStatementT1Impl(Select select, DbConnection connection, SqlColumnT<T1> column1) {
    super(select, connection);
    this.column1 = column1;
  }

  @Override
  SelectStatementT1Impl<T1> self() {
    return this;
  }

  private DbRowMapper<@NonNull T1> getRowMapper() {
    return (resultSet, rowNumber)
        -> resultSet.getNonnullValue(1, column1.getType());
  }

  @Override
  public @NonNull T1 fetchOne() {
    return fetchOne(getRowMapper());
  }

  @Override
  public List<@NonNull T1> fetch() {
    return fetch(getRowMapper());
  }

  @Override
  public Stream<@NonNull T1> stream() {
    return stream(getRowMapper());
  }

  @Override
  public @NonNull T1 fetchOneNoClose() {
    return fetchOneNoClose(getRowMapper());
  }

  @Override
  public List<@NonNull T1> fetchNoClose() {
    return fetchNoClose(getRowMapper());
  }

  @Override
  public Stream<@NonNull T1> streamNoClose() {
    return streamNoClose(getRowMapper());
  }

  @Override
  public String toString() {
    return "SelectStatementT1Impl{"
        + "column1=" + column1
        + ", " + super.toString() + '}';
  }
}