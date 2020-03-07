package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.DbRowMapper;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.dbsqlbuilder.SelectStatementT1;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;

class SelectStatementT1Impl<T1> extends SelectStatementTImpl<SelectStatementT1Impl<T1>>
    implements SelectStatementT1<T1> {

  private final SqlColumn<T1> column1;

  @SuppressWarnings("BoundedWildcard")
  SelectStatementT1Impl(String sqlText, Collection<? extends BindName> binds, DbSql sqlContext,
      SqlColumn<T1> column1) {
    super(sqlText, binds, sqlContext);
    this.column1 = column1;
  }

  @SuppressWarnings("BoundedWildcard")
  SelectStatementT1Impl(Select select, DbSql sqlContext, SqlColumn<T1> column1) {
    super(select, sqlContext);
    this.column1 = column1;
  }

  @SuppressWarnings("BoundedWildcard")
  SelectStatementT1Impl(Select select, DbConnection connection, SqlColumn<T1> column1) {
    super(select, connection);
    this.column1 = column1;
  }

  @Override
  SelectStatementT1Impl<T1> self() {
    return this;
  }

  private final class NonnullRowMapper implements DbRowMapper<@NonNull T1> {

    @Override
    public @NonNull T1 map(DbResultSet resultSet, long rowNumber) {
      return resultSet.getNonnullValue(1, column1.getType());
    }
  }

  @Override
  public @NonNull T1 fetchOne() {
    return fetchOne(new NonnullRowMapper());
  }

  @Override
  public List<@NonNull T1> fetch() {
    return fetch(new NonnullRowMapper());
  }

  @Override
  public Stream<@NonNull T1> stream() {
    return stream(new NonnullRowMapper());
  }

  @Override
  public @NonNull T1 fetchOneNoClose() {
    return fetchOneNoClose(new NonnullRowMapper());
  }

  @Override
  public List<@NonNull T1> fetchNoClose() {
    return fetchNoClose(new NonnullRowMapper());
  }

  @Override
  public Stream<@NonNull T1> streamNoClose() {
    return streamNoClose(new NonnullRowMapper());
  }

  @Override
  public String toString() {
    return "SelectStatementT1Impl{"
        + "column1=" + column1
        + ", " + super.toString() + '}';
  }
}