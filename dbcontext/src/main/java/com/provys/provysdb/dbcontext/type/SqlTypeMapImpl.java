package com.provys.provysdb.dbcontext.type;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

class SqlTypeMapImpl implements SqlTypeMap {

  private static final Logger LOG = LogManager.getLogger(SqlTypeMapImpl.class);

  private final Map<Class<?>, SqlTypeAdapter<?>> adaptersByClass;

  SqlTypeMapImpl(Collection<? extends SqlTypeAdapter<?>> adapters) {
    adaptersByClass = new ConcurrentHashMap<>(adapters.size());
    for (var adapter : adapters) {
      var old = adaptersByClass.put(adapter.getType(), Objects.requireNonNull(adapter));
      if ((old != null) && !adapter.equals(old)) {
        LOG.warn("Replaced mapping of Sql type adapter for class {}; old {}, new {}",
            adapter::getType, old::toString, adapter::toString);
      }
    }
  }

  SqlTypeMapImpl(SqlTypeAdapter<?>... adapters) {
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
  public String toString() {
    return "SqlTypeMapImpl{"
        + "adaptersByClass=" + adaptersByClass
        + '}';
  }
}
