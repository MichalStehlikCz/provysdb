package com.provys.provysdb.sqlbuilder.elements;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents variable connected with select statement, its type and value.
 */
final class BindValueImpl<T> implements BindValue<T> {

  /**
   * Get bind variable with specified name and value. Type of variable is deferred from supplied
   * value
   *
   * @param bindName  is name of bind
   * @param value is value to be used for the bind
   * @param <T>   is type of value held by this variable
   * @return bind variable with given name and value
   */
  static <T> BindValueImpl<T> ofObject(BindName bindName, @NonNull T value) {
    return new BindValueImpl<>(bindName, value);
  }

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
    return ofObject(BindNameImpl.of(name), value);
  }

  /**
   * Get bind variable with specified name, type and value.
   *
   * @param bindName  is name of bind
   * @param type  is type new bind variable should get
   * @param value is value to be used for the bind
   * @param <T>   is type of value held by this variable
   * @return bind variable with given name, type and value
   */
  static <T> BindValueImpl<T> ofType(BindName bindName, @Nullable T value, Class<T> type) {
    return new BindValueImpl<>(bindName, type, value);
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
    return ofType(BindNameImpl.of(name), value, type);
  }

  private final BindName bindName;
  private final Class<T> type;
  private final @Nullable T value;

  @SuppressWarnings("unchecked")
  private static <U> Class<U> getTypeFromValue(@NonNull U value) {
    return (Class<U>) value.getClass();
  }

  private BindValueImpl(BindName bindName, @NonNull T value) {
    this(bindName, getTypeFromValue(value), value);
  }

  private BindValueImpl(BindName bindName, Class<T> type, @Nullable T value) {
    this.bindName = bindName;
    if ((value != null) && !type.isInstance(value)) {
      throw new InternalException("Incorrect type of bind value for variable " + bindName.getName()
          + " (type " + type + ", value " + value.getClass() + ')');
    }
    this.type = type;
    this.value = value;
  }

  @Override
  public BindName getBindName() {
    return bindName;
  }

  @Override
  public String getName() {
    return bindName.getName();
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public @Nullable T getValue() {
    return value;
  }

  @Override
  public <U> @Nullable U getValue(Class<U> returnType) {
    if (!returnType.isAssignableFrom(type)) {
      throw new InternalException("Cannot convert value of bind variable " + getName() + " from "
          + this.type + " to " + type);
    }
    @SuppressWarnings("unchecked")
    U result = (U) value;
    return result;
  }

  @Override
  public <U extends T> BindValue<U> type(Class<U> newType) {
    if (!type.isAssignableFrom(newType) || ((value != null) && !newType.isInstance(value))) {
      throw new InternalException("Cannot cast bind value " + this + " to type " + newType);
    }
    // safe after checking type of value against newType
    @SuppressWarnings("unchecked")
    var newValue = (U) value;
    return new BindValueImpl<>(bindName, newType, newValue);
  }

  @Override
  public BindValue<T> value(@Nullable T newValue) {
    if (Objects.equals(value, newValue)) {
      return this;
    }
    if (!getType().isInstance(newValue)) {
      throw new InternalException("Cannot bind value " + newValue + " to bind variable " + this);
    }
    return new BindValueImpl<>(bindName, type, value);
  }

  private void combineName(BindValue<?> other) {
    if (!getName().equals(other.getName())) {
      throw new InternalException(
          "Cannot combine bind variables with different names (" + getName() + "!=" + other
              .getName() + ')');
    }
  }

  private Class<T> combineType(BindValue<?> other) {
    if (other.getType().isAssignableFrom(type)) {
      return type;
    }
    if (type.isAssignableFrom(other.getType())) {
      // assignable to Class<T> -> no risk
      @SuppressWarnings("unchecked")
      var tempType = (Class<T>) other.getType();
      return tempType;
    }
    throw new InternalException(
        "Cannot combine bind variables with different types (" + type + "!=" + other.getType()
            + ')');
  }

  private T combineValue(BindValue<T> other) {
    var otherValue = other.getValue();
    if ((otherValue == null) || otherValue.equals(value)) {
      return value;
    }
    if (value == null) {
      return otherValue;
    }
    throw new InternalException("Cannot combine bind variable " + getName() + " - values differ ("
        + this.value + "!=" + otherValue + ')');
  }

  @Override
  public BindValue<T> combine(BindValue<?> other) {
    if (equals(other)) {
      return this;
    }
    combineName(other);
    /* if we are specialisation of type and other does not have value or values are same */
    if (other.getType().isAssignableFrom(type)) {
      var otherValue = other.getValue();
      if ((otherValue == null) || otherValue.equals(value)) {
        return this;
      }
    }
    /* other is specialisation of our type and we do not have value or values are the same */
    if (type.isAssignableFrom(other.getType())
      && ((value == null) || value.equals(other.getValue()))) {
      @SuppressWarnings("unchecked")
      var result = (BindValue<T>) other;
      return result;
    }
    var newType = combineType(other);
    // safe as we already checked, that types are compatible
    @SuppressWarnings("unchecked")
    var otherTyped = (BindValue<T>) other;
    var newValue = combineValue(otherTyped);
    return new BindValueImpl<>(bindName, newType, newValue);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append('?');
    builder.addBind(this);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BindValueImpl<?> bindValue = (BindValueImpl<?>) o;
    return Objects.equals(bindName, bindValue.bindName)
        && Objects.equals(type, bindValue.type)
        && Objects.equals(value, bindValue.value);
  }

  @Override
  public int hashCode() {
    int result = bindName != null ? bindName.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BindValueImpl{"
        + "bindName=" + bindName
        + ", type=" + type
        + ", value=" + value
        + '}';
  }
}
