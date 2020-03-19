package com.provys.db.sqldb.sql;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.DbRowMapper;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindWithPos;
import com.provys.db.sql.SelectStatementT1;
import java.util.ArrayList;
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

  private static final class NonnullRowMapper<T> implements DbRowMapper<@NonNull T> {

    private final Class<? extends T> type;

    NonnullRowMapper(Class<? extends T> type) {
      this.type = type;
    }

    @Override
    public @NonNull T map(DbResultSet resultSet, long rowNumber) {
      return resultSet.getNonNullValue(1, type);
    }
  }

  private static final class OptionalRowMapper<T> implements
      DbRowMapper<Optional<@NonNull T>> {

    private final Class<T> type;

    OptionalRowMapper(Class<T> type) {
      this.type = type;
    }

    @Override
    public Optional<@NonNull T> map(DbResultSet resultSet, long rowNumber) {
      return resultSet.getOptionalValue(1, type);
    }
  }

  @Override
  public @NonNull T1 fetchNonNullOne() {
    return fetchOne(new NonnullRowMapper<>(type1));
  }

  @Override
  public @Nullable T1 fetchNullableOne() {
    return fetchOptionalOne().orElse(null);
  }

  @Override
  public Optional<T1> fetchOptionalOne() {
    return fetchOne(new OptionalRowMapper<>(type1));
  }

  @Override
  public List<@NonNull T1> fetchNonNull() {
    return fetch(new NonnullRowMapper<>(type1));
  }

  @Override
  public List<@Nullable T1> fetchNullable() {
    var listOptional = streamOptional()
        .collect(Collectors.toUnmodifiableList());
    List<@Nullable T1> result = new ArrayList<>(listOptional.size());
    for (var optionalItem : listOptional) {
      result.add(optionalItem.orElse(null));
    }
    return result;
  }

  @Override
  public Stream<@NonNull T1> streamNonNull() {
    return stream(new NonnullRowMapper<>(type1));
  }

  @Override
  public Stream<Optional<T1>> streamOptional() {
    return stream(new OptionalRowMapper<>(type1));
  }

  @Override
  public @NonNull T1 fetchNonNullOneNoClose() {
    return fetchOneNoClose(new NonnullRowMapper<>(type1));
  }

  @Override
  public @Nullable T1 fetchNullableOneNoClose() {
    return fetchOptionalOneNoClose().orElse(null);
  }

  @Override
  public Optional<T1> fetchOptionalOneNoClose() {
    return fetchOneNoClose(new OptionalRowMapper<>(type1));
  }

  @Override
  public List<@NonNull T1> fetchNonNullNoClose() {
    return fetchNoClose(new NonnullRowMapper<>(type1));
  }

  @Override
  public List<@Nullable T1> fetchNullableNoClose() {
    var listOptional = streamOptionalNoClose()
        .collect(Collectors.toUnmodifiableList());
    List<@Nullable T1> result = new ArrayList<>(listOptional.size());
    for (var optionalItem : listOptional) {
      result.add(optionalItem.orElse(null));
    }
    return result;
  }

  @Override
  public Stream<@NonNull T1> streamNonNullNoClose() {
    return streamNoClose(new NonnullRowMapper<>(type1));
  }

  @Override
  public Stream<Optional<T1>> streamOptionalNoClose() {
    return streamNoClose(new OptionalRowMapper<>(type1));
  }

  @Override
  public String toString() {
    return "SelectStatementT1Impl{"
        + "type1=" + type1
        + ", " + super.toString() + '}';
  }
}