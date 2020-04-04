package com.provys.db.sqlquery.literals;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.common.exception.InternalException;
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

/**
 * Implements literal handler using type handlers for individual types.
 * Provides default instance based on adapters, present in this package. At the same time, allows
 * instantiation with specified set of handlers.
 */
public class SqlLiteralTypeHandlerMap implements SqlLiteralHandler {

  private static final SqlLiteralTypeHandlerMap DEFAULT_MAP = new SqlLiteralTypeHandlerMap(
      SqlLiteralBooleanHandler.getInstance(),
      SqlLiteralByteHandler.getInstance(),
      SqlLiteralIntegerHandler.getInstance(),
      SqlLiteralDoubleHandler.getInstance(),
      SqlLiteralStringHandler.getInstance(),
      SqlLiteralBigDecimalHandler.getInstance(),
      SqlLiteralBigIntegerHandler.getInstance(),
      SqlLiteralDtUidHandler.getInstance(),
      SqlLiteralDtDateHandler.getInstance(),
      SqlLiteralDtDateTimeHandler.getInstance()
  );

  public static SqlLiteralTypeHandlerMap getDefaultMap() {
    return DEFAULT_MAP;
  }

  private static final Logger LOG = LogManager.getLogger(SqlLiteralTypeHandlerMap.class);

  private final Map<Class<?>, SqlLiteralTypeHandler<?>> handlersByType;

  /**
   * Create new type map with supplied type handlers.
   *
   * @param handlers are handlers that should form this handler map
   */
  public SqlLiteralTypeHandlerMap(Collection<? extends SqlLiteralTypeHandler<?>> handlers) {
    handlersByType = new ConcurrentHashMap<>(handlers.size());
    for (var handler : handlers) {
      var old = handlersByType.put(handler.getType(), Objects.requireNonNull(handler));
      if ((old != null) && !handler.equals(old)) {
        LOG.warn("Replaced mapping of Sql literal type handler for class {}; old {}, new {}",
            handler::getType, old::toString, handler::toString);
      }
    }
  }

  public SqlLiteralTypeHandlerMap(SqlLiteralTypeHandler<?>... handlers) {
    this(Arrays.asList(handlers));
  }

  private <T> @Nullable SqlLiteralTypeHandler<T> getHandlerSuper(Class<T> type) {
    Class<?> currentType = type;
    // first try to find adapter for type in superclasses
    while (currentType != null) {
      var result = handlersByType.get(currentType);
      if (result != null) {
        @SuppressWarnings("unchecked")
        var finalResult = (SqlLiteralTypeHandler<T>) result;
        return finalResult;
      }
      currentType = currentType.getSuperclass();
    }
    return null;
  }

  private <T> @Nullable SqlLiteralTypeHandler<T> getHandlerInterface(Class<T> type) {
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
      var result = handlersByType.get(currentType);
      if (result != null) {
        @SuppressWarnings("unchecked")
        var typedResult = (SqlLiteralTypeHandler<T>) result;
        return typedResult;
      }
      for (var iface : currentType.getInterfaces()) {
        classes.addLast(iface);
      }
    }
    return null;
  }

  /**
   * Get handler, translating literals for given type.
   *
   * @param type is type we want to find handler for
   * @param <T> is type parameter corresponding to type of handler
   * @return type handler, capable oh handling given type
   */
  public <T> SqlLiteralTypeHandler<T> getHandler(Class<T> type) {
    // first try to find in supertype... mostly successful so better to try it before heavy weight
    // search
    var result = getHandlerSuper(type);
    // next go through class hierarchy once more, but this time use interfaces
    if (result == null) {
      result = getHandlerInterface(type);
      if (result == null) {
        throw new InternalException("No sql type adapter found for class " + type);
      }
    }
    return result;
  }


  @Override
  public <T> String getLiteral(@Nullable T value, Class<T> type) {
    return getHandler(type).getLiteral(value);
  }

  private <T> String getLiteralT(T value) {
    @SuppressWarnings("unchecked")
    var type = (Class<T>) value.getClass();
    return getLiteral(value, type);
  }

  @Override
  public String getLiteral(Object value) {
    return getLiteralT(value);
  }

  @Override
  public <T> void appendLiteral(StringBuilder builder, @Nullable T value, Class<T> type) {
    getHandler(type).appendLiteral(builder, value);
  }

  private <T> void appendLiteralT(StringBuilder builder, T value) {
    @SuppressWarnings("unchecked")
    var type = (Class<T>) value.getClass();
    appendLiteral(builder, value, type);
  }

  @Override
  public void appendLiteral(StringBuilder builder, Object value) {
    appendLiteralT(builder, value);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlLiteralTypeHandlerMap that = (SqlLiteralTypeHandlerMap) o;
    return handlersByType.equals(that.handlersByType);
  }

  @Override
  public int hashCode() {
    return handlersByType.hashCode();
  }

  @Override
  public String toString() {
    return "SqlLiteralTypeHandlerMap{"
        + "handlersByType=" + handlersByType
        + '}';
  }
}
