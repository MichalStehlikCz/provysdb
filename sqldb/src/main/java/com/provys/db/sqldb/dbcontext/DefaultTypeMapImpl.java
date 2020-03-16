package com.provys.db.sqldb.dbcontext;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.db.sqldb.types.SqlTypeAdapterBigDecimal;
import com.provys.db.sqldb.types.SqlTypeAdapterBigInteger;
import com.provys.db.sqldb.types.SqlTypeAdapterBoolean;
import com.provys.db.sqldb.types.SqlTypeAdapterByte;
import com.provys.db.sqldb.types.SqlTypeAdapterDtDate;
import com.provys.db.sqldb.types.SqlTypeAdapterDtDateTime;
import com.provys.db.sqldb.types.SqlTypeAdapterDtUid;
import com.provys.db.sqldb.types.SqlTypeAdapterInteger;
import com.provys.db.sqldb.types.SqlTypeAdapterString;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default implementation of type map. Uses types of supplied adapters as their mapping, inheritance
 * primarily via superclasses, interfaces are checked afterwards.
 */
public class DefaultTypeMapImpl implements SqlTypeMap {

  private static final SqlTypeMap DEFAULT_MAP = new DefaultTypeMapImpl(
      SqlTypeAdapterBoolean.getInstance(),
      SqlTypeAdapterByte.getInstance(),
      SqlTypeAdapterInteger.getInstance(),
      SqlTypeAdapterString.getInstance(),
      SqlTypeAdapterBigDecimal.getInstance(),
      SqlTypeAdapterBigInteger.getInstance(),
      SqlTypeAdapterDtUid.getInstance(),
      SqlTypeAdapterDtDate.getInstance(),
      SqlTypeAdapterDtDateTime.getInstance()
  );

  public static SqlTypeMap getDefaultMap() {
    return DEFAULT_MAP;
  }

  private static final Logger LOG = LogManager.getLogger(DefaultTypeMapImpl.class);

  private final Map<Class<?>, SqlTypeAdapter<?>> adaptersByClass;

  /**
   * Create new type map with supplied adapters.
   *
   * @param adapters are adapters that should form this adapter map
   */
  public DefaultTypeMapImpl(Collection<? extends SqlTypeAdapter<?>> adapters) {
    adaptersByClass = new ConcurrentHashMap<>(adapters.size());
    for (var adapter : adapters) {
      var old = adaptersByClass.put(adapter.getType(), Objects.requireNonNull(adapter));
      if ((old != null) && !adapter.equals(old)) {
        LOG.warn("Replaced mapping of Sql type adapter for class {}; old {}, new {}",
            adapter::getType, old::toString, adapter::toString);
      }
    }
  }

  public DefaultTypeMapImpl(SqlTypeAdapter<?>... adapters) {
    this(Arrays.asList(adapters));
  }

  private static <T> @Nullable SqlTypeAdapter<T> getAdapterSuper(Class<T> type,
      Map<Class<?>, SqlTypeAdapter<?>> adapterMap) {
    Class<?> currentType = type;
    // first try to find adapter for type in superclasses
    while (currentType != null) {
      var result = adapterMap.get(currentType);
      if (result != null) {
        //noinspection unchecked
        return (SqlTypeAdapter<T>) result;
      }
      currentType = currentType.getSuperclass();
    }
    return null;
  }

  private static <T> @Nullable SqlTypeAdapter<T> getAdapterInterface(Class<T> type,
      Map<Class<?>, SqlTypeAdapter<?>> adapterMap) {
    Deque<Class<?>> classes = new ArrayDeque<>(3);
    Class<?> currentType = type;
    // first put superclasses in queue
    while (currentType != null) {
      classes.add(currentType);
      currentType = currentType.getSuperclass();
    }
    // next go through queue, check if there is adapter for it and register its superinterfaces
    while (!classes.isEmpty()) {
      currentType = castNonNull(classes.pollFirst()); // is non-null, because classes was not empty
      var result = adapterMap.get(currentType);
      if (result != null) {
        @SuppressWarnings("unchecked")
        var typedResult = (SqlTypeAdapter<T>) result;
        return typedResult;
      }
      for (var iface : currentType.getInterfaces()) {
        classes.addLast(iface);
      }
    }
    return null;
  }

  @Override
  public <T> SqlTypeAdapter<T> getAdapter(Class<T> type) {
    // first try to find in supertype... mostly successful so better to try it before heavy weight
    // search
    var result = getAdapterSuper(type, adaptersByClass);
    // next go through class hierarchy once more, but this time use interfaces
    if (result == null) {
      result = getAdapterInterface(type, adaptersByClass);
      if (result == null) {
        throw new InternalException("No sql type adapter found for class " + type);
      }
    }
    return result;
  }

  @Override
  public int getSqlType(Class<?> type) {
    return getAdapter(type).getSqlType();
  }

  @Override
  public Optional<String> getTypeName(Class<?> type) {
    return getAdapter(type).getTypeName();
  }

  @Override
  public <T> @NonNull T readNonNullValue(DbResultSet resultSet, int columnIndex, Class<T> type) {
    return getAdapter(type).readNonNullValue(resultSet, columnIndex);
  }

  @Override
  public <T> @NonNull T readNonNullValue(DbResultSet resultSet, String columnLabel, Class<T> type) {
    return getAdapter(type).readNonNullValue(resultSet, columnLabel);
  }

  @Override
  public <T> @Nullable T readNullableValue(DbResultSet resultSet, int columnIndex, Class<T> type) {
    return getAdapter(type).readNullableValue(resultSet, columnIndex);
  }

  @Override
  public <T> @Nullable T readNullableValue(DbResultSet resultSet, String columnLabel,
      Class<T> type) {
    return getAdapter(type).readNullableValue(resultSet, columnLabel);
  }

  @Override
  public <T> Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, int columnIndex,
      Class<T> type) {
    return getAdapter(type).readOptionalValue(resultSet, columnIndex);
  }

  @Override
  public <T> Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, String columnLabel,
      Class<T> type) {
    return getAdapter(type).readOptionalValue(resultSet, columnLabel);
  }

  @Override
  public <T> void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable T value,
      Class<T> type) {
    getAdapter(type).bindValue(statement, parameterIndex, value);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex, Object value) {
    // we know that it is safe to call bindValue with class retrieved from value
    @SuppressWarnings("unchecked")
    Class<Object> type = (Class<Object>) value.getClass();
    bindValue(statement, parameterIndex, value, type);
  }

  @Override
  public <T> String getLiteral(@Nullable T value, Class<T> type) {
    return getAdapter(type).getLiteral(value);
  }

  @Override
  public String getLiteral(Object value) {
    // we know that it is safe to call bindValue with class retrieved from value
    @SuppressWarnings("unchecked")
    Class<Object> type = (Class<Object>) value.getClass();
    return getLiteral(value, type);
  }

  /**
   * Append literal corresponding to given value to supplied builder.
   *
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value   is value literal should be created for
   * @param type    is Java type of literal
   */
  @Override
  public <T> void appendLiteral(StringBuilder builder, @Nullable T value, Class<T> type) {
    getAdapter(type).appendLiteral(builder, value);
  }

  /**
   * Append literal corresponding to given value.
   *
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value   is value literal should be created for
   */
  @Override
  public void appendLiteral(StringBuilder builder, Object value) {
    // we know that it is safe to call bindValue with class retrieved from value
    @SuppressWarnings("unchecked")
    Class<Object> type = (Class<Object>) value.getClass();
    appendLiteral(builder, value, type);
  }

  @Override
  public String toString() {
    return "SqlTypeMapImpl{"
        + "adaptersByClass=" + adaptersByClass
        + '}';
  }
}
