package com.provys.db.querybuilder.elements;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sql.BindName;
import com.provys.db.sqlquery.queryold.name.BindNameImpl;
import com.provys.db.querybuilder.BindValueBuilder;
import com.provys.db.querybuilder.BindWithType;
import com.provys.provysdb.sql.CodeBuilder;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents variable connected with select statement, its type and value.
 */
final class BindValueBuilderImpl<T> extends ColumnExpressionBaseWithType<T> implements
    BindValueBuilder<T> {

  /**
   * Get bind variable with specified name and value. Type of variable is deferred from supplied
   * value
   *
   * @param bindName  is name of bind
   * @param value is value to be used for the bind
   * @param <T>   is type of value held by this variable
   * @return bind variable with given name and value
   */
  static <T> BindValueBuilderImpl<T> ofObject(BindName bindName, @NonNull T value) {
    return new BindValueBuilderImpl<>(bindName, value);
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
  static <T> BindValueBuilderImpl<T> ofObject(String name, @NonNull T value) {
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
  static <T> BindValueBuilderImpl<T> ofType(BindName bindName, @Nullable T value, Class<T> type) {
    return new BindValueBuilderImpl<>(bindName, type, value);
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
  static <T> BindValueBuilderImpl<T> ofType(String name, @Nullable T value, Class<T> type) {
    return ofType(BindNameImpl.of(name), value, type);
  }

  private final BindName bindName;
  private final @Nullable T value;

  @SuppressWarnings("unchecked")
  private static <U> Class<U> getTypeFromValue(@NonNull U value) {
    return (Class<U>) value.getClass();
  }

  private BindValueBuilderImpl(BindName bindName, @NonNull T value) {
    this(bindName, getTypeFromValue(value), value);
  }

  private BindValueBuilderImpl(BindName bindName, Class<T> type, @Nullable T value) {
    super(type);
    this.bindName = bindName;
    if ((value != null) && !type.isInstance(value)) {
      throw new InternalException("Incorrect type of bind value for variable " + bindName.getName()
          + " (type " + type + ", value " + value.getClass() + ')');
    }
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
  public @Nullable T getValue() {
    return value;
  }

  @Override
  public <U> @Nullable U getValue(Class<U> returnType) {
    if (!returnType.isAssignableFrom(getType())) {
      throw new InternalException("Cannot convert value of bind variable " + getName() + " from "
          + this.getType() + " to " + returnType);
    }
    @SuppressWarnings("unchecked")
    U result = (U) value;
    return result;
  }

  @Override
  public <U extends T> BindValueBuilder<U> type(Class<U> newType) {
    if (!getType().isAssignableFrom(newType) || ((value != null) && !newType.isInstance(value))) {
      throw new InternalException("Cannot cast bind value " + this + " to type " + newType);
    }
    // safe after checking type of value against newType
    @SuppressWarnings("unchecked")
    var newValue = (U) value;
    return new BindValueBuilderImpl<>(bindName, newType, newValue);
  }

  @Override
  public BindValueBuilder<T> value(@Nullable T newValue) {
    return valueUnsafe(newValue);
  }

  @Override
  public BindValueBuilder<T> valueUnsafe(@Nullable Object newValue) {
    if (Objects.equals(value, newValue)) {
      return this;
    }
    if ((newValue != null) && !getType().isInstance(newValue)) {
      throw new InternalException("Cannot bind value " + newValue + " to bind variable " + this);
    }
    return new BindValueBuilderImpl<>(bindName, getType(), value);
  }

  private void combineName(BindWithType other) {
    if (!getName().equals(other.getName())) {
      throw new InternalException(
          "Cannot combine bind variables with different names (" + getName() + "!=" + other
              .getName() + ')');
    }
  }

  private Class<T> combineType(BindValueBuilder<?> other) {
    if (other.getType().isAssignableFrom(getType())) {
      return getType();
    }
    if (getType().isAssignableFrom(other.getType())) {
      // assignable to Class<T> -> no risk
      @SuppressWarnings("unchecked")
      var tempType = (Class<T>) other.getType();
      return tempType;
    }
    throw new InternalException(
        "Cannot combine bind variables with different types (" + getType() + "!=" + other.getType()
            + ')');
  }

  private T combineValue(BindValueBuilder<? extends T> other) {
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
  public BindValueBuilder<T> combine(BindValueBuilder<?> other) {
    if (equals(other)) {
      return this;
    }
    combineName(other);
    /* if we are specialisation of type and other does not have value or values are same */
    if (other.getType().isAssignableFrom(getType())) {
      var otherValue = other.getValue();
      if ((otherValue == null) || otherValue.equals(value)) {
        return this;
      }
    }
    /* other is specialisation of our type and we do not have value or values are the same */
    if (getType().isAssignableFrom(other.getType())
      && ((value == null) || value.equals(other.getValue()))) {
      @SuppressWarnings("unchecked")
      var result = (BindValueBuilder<T>) other;
      return result;
    }
    var newType = combineType(other);
    // safe as we already checked, that types are compatible
    @SuppressWarnings("unchecked")
    var otherTyped = (BindValueBuilder<T>) other;
    var newValue = combineValue(otherTyped);
    return new BindValueBuilderImpl<>(bindName, newType, newValue);
  }

  @Override
  public void appendExpression(CodeBuilder builder) {
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
    if (!super.equals(o)) {
      return false;
    }
    BindValueBuilderImpl<?> bindValue = (BindValueBuilderImpl<?>) o;
    return Objects.equals(bindName, bindValue.bindName)
        && Objects.equals(value, bindValue.value);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (bindName != null ? bindName.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "BindValueImpl{"
        + "bindName=" + bindName
        + ", value=" + value
        + ", " + super.toString() + '}';
  }
}
