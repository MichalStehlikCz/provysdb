package com.provys.db.sqlquery.query;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.DbRowMapper;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindWithPos;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;

class SelectStatementT2Impl<T1, T2>
    extends SelectStatementTImpl<SelectStatementT2Impl<T1, T2>>
    implements SelectStatementT2<T1, T2> {

  private final Class<? extends T1> type1;
  private final Class<? extends T2> type2;

  SelectStatementT2Impl(String sqlText,
      Collection<BindWithPos> binds,
      Map<BindName, ?> bindValues,
      DbConnection connection, Class<? extends T1> type1, Class<? extends T2> type2) {
    super(sqlText, binds, bindValues, connection);
    this.type1 = type1;
    this.type2 = type2;
  }

  SelectStatementT2Impl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, ?> bindValues, DbContext dbContext, Class<? extends T1> type1,
      Class<? extends T2> type2) {
    super(sqlText, binds, bindValues, dbContext);
    this.type1 = type1;
    this.type2 = type2;
  }

  @Override
  SelectStatementT2Impl<T1, T2> self() {
    return this;
  }

  private static final class RowMapperT2<T1, T2> implements DbRowMapper<TupleT2<T1, T2>> {

    private final Class<? extends T1> type1;
    private final Class<? extends T2> type2;

    RowMapperT2(Class<? extends T1> type1, Class<? extends T2> type2) {
      this.type1 = type1;
      this.type2 = type2;
    }

    @Override
    public TupleT2<T1, T2> map(DbResultSet resultSet, long rowNumber) {
      return new TupleT2<>(resultSet.getNullableValue(1, type1),
          resultSet.getNullableValue(2, type2));
    }
  }

  @Override
  public TupleT2<T1, T2> fetchOne() {
    return fetchOne(new RowMapperT2<>(type1, type2));
  }

  @Override
  public List<TupleT2<T1, T2>> fetch() {
    return fetch(new RowMapperT2<>(type1, type2));
  }

  @Override
  public Stream<TupleT2<T1, T2>> stream() {
    return stream(new RowMapperT2<>(type1, type2));
  }

  @Override
  public TupleT2<T1, T2> fetchOneNoClose() {
    return fetchOneNoClose(new RowMapperT2<>(type1, type2));
  }

  @Override
  public List<TupleT2<T1, T2>> fetchNoClose() {
    return fetchNoClose(new RowMapperT2<>(type1, type2));
  }

  @Override
  public Stream<TupleT2<T1, T2>> streamNoClose() {
    return streamNoClose(new RowMapperT2<>(type1, type2));
  }

  @Override
  public String toString() {
    return "SelectStatementT2Impl{"
        + "type1=" + type1
        + ", type2=" + type2
        + ", " + super.toString() + '}';
  }
}
