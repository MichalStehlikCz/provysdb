package com.provys.db.sqldb.sql;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.DbRowMapper;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindWithPos;
import com.provys.db.sql.SelectStatementT1;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

class SelectStatementT1Impl<T1> extends SelectStatementTImpl<SelectStatementT1Impl<T1>>
    implements SelectStatementT1<T1> {

  private final Class<T1> type1;

  SelectStatementT1Impl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, @Nullable Object> bindValues,
      DbConnection connection, Class<T1> type1) {
    super(sqlText, binds, bindValues, connection);
    this.type1 = type1;
  }

  SelectStatementT1Impl(String sqlText, Collection<BindWithPos> binds,
      Map<BindName, @Nullable Object> bindValues, DbContext dbContext, Class<T1> type1) {
    super(sqlText, binds, bindValues, dbContext);
    this.type1 = type1;
  }

  @Override
  SelectStatementT1Impl<T1> self() {
    return this;
  }

  private final class NonnullRowMapper implements DbRowMapper<T1> {

    @Override
    public @NonNull T1 map(DbResultSet resultSet, long rowNumber) {
      return resultSet.getNonNullValue(1, type1);
    }
  }

  private final class OptionalRowMapper implements DbRowMapper<Optional<T1>> {

    @Override
    public Optional<T1> map(DbResultSet resultSet, long rowNumber) {
      return resultSet.getOptionalValue(1, type1);
    }
  }

  @Override
  public @NonNull T1 fetchNonNullOne() {
    return fetchOne(new NonnullRowMapper());
  }

  @Override
  public @Nullable T1 fetchNullableOne() {
    return fetchOptionalOne().orElse(null);
  }

  @Override
  public Optional<T1> fetchOptionalOne() {
    return fetchOne(new OptionalRowMapper());
  }

  @Override
  public List<@NonNull T1> fetchNonNull() {
    return fetch(new NonnullRowMapper());
  }

  @Override
  public List<@Nullable T1> fetchNullable() {
    return streamNullable().collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Stream<@NonNull T1> streamNonNull() {
    return stream(new NonnullRowMapper());
  }

  @Override
  public Stream<@Nullable T1> streamNullable() {
    return stream(new OptionalRowMapper()).map(optValue -> optValue.orElse(null));
  }

  @Override
  public @NonNull T1 fetchNonNullOneNoClose() {
    return fetchOneNoClose(new NonnullRowMapper());
  }

  @Override
  public @Nullable T1 fetchNullableOneNoClose() {
    return fetchOptionalOneNoClose().orElse(null);
  }

  @Override
  public Optional<T1> fetchOptionalOneNoClose() {
    return fetchOneNoClose(new OptionalRowMapper());
  }

  @Override
  public List<@NonNull T1> fetchNonNullNoClose() {
    return fetchNoClose(new NonnullRowMapper());
  }

  @Override
  public List<@Nullable T1> fetchNullableNoClose() {
    return streamNullableNoClose().collect(Collectors.toUnmodifiableList());
  }

  @Override
  public Stream<@NonNull T1> streamNonNullNoClose() {
    return streamNoClose(new NonnullRowMapper());
  }

  @Override
  public Stream<@Nullable T1> streamNullableNoClose() {
    return streamNoClose(new OptionalRowMapper()).map(optValue -> optValue.orElse(null));
  }

  @Override
  public String toString() {
    return "SelectStatementT1Impl{"
        + "type1=" + type1
        + ", " + super.toString() + '}';
  }
}