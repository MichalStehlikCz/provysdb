package com.provys.db.defaultdb.types;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeHandler;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
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
public final class SqlTypeMap implements SqlTypeHandler {

  private static final SqlTypeMap DEFAULT_MAP = new SqlTypeMap(
      SqlTypeAdapterBoolean.getInstance(),
      SqlTypeAdapterByte.getInstance(),
      SqlTypeAdapterInteger.getInstance(),
      SqlTypeAdapterDouble.getInstance(),
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

  private static final Logger LOG = LogManager.getLogger(SqlTypeMap.class);

  private final Map<Class<?>, SqlTypeAdapter<?>> adaptersByType;

  /**
   * Create new type map with supplied adapters.
   *
   * @param adapters are adapters that should form this adapter map
   */
  public SqlTypeMap(Collection<? extends SqlTypeAdapter<?>> adapters) {
    adaptersByType = new ConcurrentHashMap<>(adapters.size());
    for (var adapter : adapters) {
      var old = adaptersByType.put(adapter.getType(), Objects.requireNonNull(adapter));
      if ((old != null) && !adapter.equals(old)) {
        LOG.warn("Replaced mapping of Sql type adapter for class {}; old {}, new {}",
            adapter::getType, old::toString, adapter::toString);
      }
    }
  }

  public SqlTypeMap(SqlTypeAdapter<?>... adapters) {
    this(Arrays.asList(adapters));
  }

  private <T> @Nullable SqlTypeAdapter<T> getAdapterSuper(Class<T> type) {
    Class<?> currentType = type;
    // first try to find adapter for type in superclasses
    while (currentType != null) {
      var result = adaptersByType.get(currentType);
      if (result != null) {
        @SuppressWarnings("unchecked")
        var finalResult = (SqlTypeAdapter<T>) result;
        return finalResult;
      }
      currentType = currentType.getSuperclass();
    }
    return null;
  }

  private <T> @Nullable SqlTypeAdapter<T> getAdapterInterface(Class<T> type) {
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
      var result = adaptersByType.get(currentType);
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

  /**
   * Get adapter, handling supplied type.
   *
   * @param type is type we want to find adapter for
   * @param <T> is type parameter corresponding to type of adapter
   * @return type adapter, capable oh handling given type
   */
  public <T> SqlTypeAdapter<T> getAdapter(Class<T> type) {
    // first try to find in supertype... mostly successful so better to try it before heavy weight
    // search
    var result = getAdapterSuper(type);
    // next go through class hierarchy once more, but this time use interfaces
    if (result == null) {
      result = getAdapterInterface(type);
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

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this DefaultTypeMapImpl
   */
  private Object writeReplace() {
    return new SerializationProxy(this);
  }

  /**
   * Should be serialized via proxy, thus no direct deserialization should occur.
   *
   * @param stream is stream from which object is to be read
   * @throws InvalidObjectException always
   */
  private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Use Serialization Proxy instead.");
  }

  private static final class SerializationProxy implements Serializable {

    private static final long serialVersionUID = -526122726670289167L;
    private @Nullable Collection<SqlTypeAdapter<?>> adapters;

    SerializationProxy() {
    }

    SerializationProxy(SqlTypeMap value) {
      this.adapters = value.adaptersByType.values();
    }

    private Object readResolve() {
      var value = new SqlTypeMap(Objects.requireNonNull(adapters));
      if (value.equals(DEFAULT_MAP)) {
        return DEFAULT_MAP;
      }
      return value;
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlTypeMap that = (SqlTypeMap) o;
    return adaptersByType.equals(that.adaptersByType);
  }

  @Override
  public int hashCode() {
    return adaptersByType.hashCode();
  }

  @Override
  public String toString() {
    return "SqlTypeMap{"
        + "adaptersByType=" + adaptersByType
        + '}';
  }
}
