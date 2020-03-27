package com.provys.db.sql;

import com.provys.common.exception.InternalException;
import java.io.Serializable;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Immutable data class representing bind variable in SQL statement. Note that while BindVariable
 * strives to be immutable, this contract might be broken if mutable object is assigned as value.
 * System does not restrict assignment of such object, but it is generally only recommended to
 * assign value objects to bind variables.
 */
public final class BindVariable implements Serializable {

  private static final long serialVersionUID = 1L;

  private final BindName name;
  private final Class<?> type;
  private final @Nullable Object value;

  /**
   * Create new bind variable.
   *
   * @param name  is name of new bind variable
   * @param type  is type of new bind variable
   * @param value is default value for bind variable
   */
  public BindVariable(BindName name, Class<?> type, @Nullable Object value) {
    this.name = name;
    this.type = type;
    if ((value != null) && !type.isInstance(value)) {
      throw new InternalException("Bind variable value " + value + " does not match type " + type);
    }
    this.value = value;
  }

  /**
   * Create new bind variable.
   *
   * @param name  is name of new bind variable
   * @param type  is type of new bind variable
   * @param value is default value for bind variable
   */
  public BindVariable(String name, Class<?> type, @Nullable Object value) {
    this(BindName.valueOf(name), type, value);
  }

  /**
   * Name of bind variable.
   *
   * @return name of bind variable
   */
  public BindName getName() {
    return name;
  }

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * Value of bind variable.
   *
   * @return value held in this bind variable
   */
  public @Nullable Object getValue() {
    return value;
  }

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this BindVariable
   */
  private Object writeReplace() {
    return new SerializationProxy(this);
  }

  private static final class SerializationProxy implements Serializable {

    private static final long serialVersionUID = 1L;

    private @Nullable BindName name;
    private @Nullable Class<?> type;
    // we know that serialization might fail... and we do not care, at least not at the moment
    @SuppressWarnings({"java:S1948", "NonSerializableFieldInSerializableClass"})
    private @Nullable Object value;

    SerializationProxy() {
    }

    SerializationProxy(BindVariable bindVariable) {
      this.name = bindVariable.getName();
      this.type = bindVariable.getType();
      this.value = bindVariable.getValue();
    }

    private Object readResolve() {
      return new BindVariable(Objects.requireNonNull(name), Objects.requireNonNull(type), value);
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
    BindVariable that = (BindVariable) o;
    return Objects.equals(name, that.name)
        && type == that.type
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BindVariable{"
        + "name=" + name
        + ", type=" + type
        + ", value=" + value
        + '}';
  }
}
