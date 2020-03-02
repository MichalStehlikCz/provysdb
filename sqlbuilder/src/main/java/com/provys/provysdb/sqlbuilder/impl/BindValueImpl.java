package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.BindValueT;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents variable connected with select statement, its type and value.
 */
final class BindValueImpl<T> extends BindNameImpl implements BindValueT<T> {

  /**
   * Get bind variable with specified name and value. Type of variable is deferred from supplied
   * value
   *
   * @param name  is name of bind
   * @param value is value to be used for the bind
   * @param <T>   is type of value held by this variable
   * @return bind variable with given name and value
   */
  static <T> BindValueImpl<T> ofObject(String name, @NonNull T value) {
    return new BindValueImpl<>(name, value);
  }

  /**
   * Get bind variable with specified name, type and value.
   *
   * @param name  is name of bind
   * @param type  is type new bind variable should get
   * @param value is value to be used for the bind
   * @param <T>   is type of value held by this variable
   * @return bind variable with given name, type and value
   */
  static <T> BindValueImpl<T> ofType(String name, @Nullable T value, Class<T> type) {
    return new BindValueImpl<>(name, type, value);
  }

  private final Class<T> type;
  private final @Nullable T value;

  private BindValueImpl(String name, @NonNull T value) {
    //noinspection unchecked
    this(name, (Class<T>) value.getClass(), value);
  }

  private BindValueImpl(String name, Class<T> type, @Nullable T value) {
    super(name);
    if ((value != null) && !type.isInstance(value)) {
      throw new InternalException("Incorrect type of bind value for variable " + name
          + " (type " + type + ", value " + value.getClass() + ')');
    }
    this.type = Objects.requireNonNull(type);
    this.value = value;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public <U> @Nullable U getValue(Class<U> returnType) {
    if (!returnType.isAssignableFrom(type)) {
      throw new InternalException("Cannot convert value of bind variable " + getName() + " from "
          + this.type + " to " + type);
    }
    //noinspection unchecked
    return (U) value;
  }

  @Override
  public @Nullable T getValue() {
    return value;
  }

  @Override
  public BindValueT<T> withValue(@Nullable T newValue) {
    if (Objects.equals(value, newValue)) {
      return this;
    }
    return new BindValueImpl<>(getName(), type, value);
  }

  @Override
  public BindValueT<T> combine(BindName other) {
    if (equals(other)) {
      return this;
    }
    if (!getName().equals(other.getName())) {
      throw new InternalException(
          "Cannot combine bind variables with different names (" + getName() + "!=" + other
              .getName() + ')');
    }
    if (!(other instanceof BindValue)) {
      return this;
    }
    var otherVariable = (BindValue) other;
    if (type != otherVariable.getType()) {
      throw new InternalException(
          "Cannot combine bind variables with different types (" + type + "!=" + otherVariable
              .getType() + ')');
    }
    if (Objects.equals(getValue(Object.class), value)) {
      return this;
    }
    if (value == null) {
      if (!(otherVariable instanceof BindValueT)) {
        throw new InternalException(
            "Class implementing BindValue is expected to implement BindValueT as well");
      }
      //noinspection unchecked - we verified that values are of the same type few lines above
      return (BindValueT<T>) otherVariable;
    }
    if (otherVariable.getValue(Object.class) == null) {
      return this;
    }
    throw new InternalException("Cannot combine bind variable " + getName() + " - values differ ("
        + this.value + "!=" + otherVariable.getValue(Object.class) + ')');
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BindValueImpl<?> bindValue = (BindValueImpl<?>) o;
    return (type == bindValue.type)
        && Objects.equals(value, bindValue.value);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BindValueImpl{"
        + "type=" + type
        + ", value=" + value
        + ", " + super.toString() + '}';
  }
}
