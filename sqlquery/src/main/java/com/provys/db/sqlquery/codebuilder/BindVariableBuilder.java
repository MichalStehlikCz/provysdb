package com.provys.db.sqlquery.codebuilder;

import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class BindVariableBuilder {

  private final BindName name;
  private Class<?> type;
  private @Nullable Object value;

  BindVariableBuilder(BindName name, Class<?> type, @Nullable Object value) {
    this.name = name;
    this.type = type;
    if ((value != null) && !type.isInstance(value)) {
      throw new InternalException("Failed to create bind variable builder " + name.getName()
          + "; value " + value + " not compatible with type " + type);
    }
    this.value = value;
  }

  BindVariableBuilder(BindName name, Class<?> type) {
    this(name, type, null);
  }

  BindVariableBuilder(BindVariable bindVariable) {
    this(bindVariable.getName(), bindVariable.getType(), bindVariable.getValue());
  }

  /**
   * Value of field name.
   *
   * @return value of field name
   */
  BindName getName() {
    return name;
  }

  /**
   * Value of field type.
   *
   * @return value of field type
   */
  Class<?> getType() {
    return type;
  }

  /**
   * Value of field value.
   *
   * @return value of field value
   */
  @Nullable Object getValue() {
    return value;
  }

  void combineType(Class<?> newType) {
    // newType is super of type - no change needed
    if (newType.isAssignableFrom(type)) {
      return;
    }
    if (type.isAssignableFrom(newType)) {
      if ((value != null) && !newType.isInstance(value)) {
        throw new InternalException("Failed to assign new type to bind variable builder "
            + name.getName() + "; value " + value + " not compatible with new type " + newType);
      }
      type = newType;
      return;
    }
    throw new InternalException("Attempt to assign incompatible type to variable builder "
        + name.getName() + "; old type " + type + ", new type " + newType);
  }

  void combineValue(@Nullable Object newValue) {
    if ((newValue == null) || newValue.equals(value)) {
      return;
    }
    if (value == null) {
      if (!type.isInstance(newValue)) {
        throw new InternalException("Failed to assign new value to bind variable builder "
            + name.getName() + "; new value " + newValue + " not compatible with type " + type);
      }
      value = newValue;
      return;
    }
    throw new InternalException("Attempt to assign different value to variable builder "
        + name.getName() + "; old value " + value + ", new value " + newValue);
  }

  void combineVariable(BindVariable bindVariable) {
    if (!bindVariable.getName().equals(name)) {
      throw new InternalException("Cannot combine bind variables with different names "
          + bindVariable.getName() + " and " + name);
    }
    combineType(bindVariable.getType());
    combineValue(bindVariable.getValue());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BindVariableBuilder that = (BindVariableBuilder) o;
    return Objects.equals(name, that.name)
        && (type == that.type)
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
    return "BindVariableBuilder{"
        + "name=" + name
        + ", type=" + type
        + ", value=" + value
        + '}';
  }
}
