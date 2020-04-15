package com.provys.db.query.names;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.ImmutableTypeParameter;
import com.provys.common.exception.InternalException;
import com.provys.common.types.ProvysClassSerializer;
import com.provys.common.types.TypeMapImpl;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Immutable data class representing bind variable in SQL statement. Note that while BindVariable
 * strives to be immutable, this contract might be broken if mutable object is assigned as value.
 * System does not restrict assignment of such object, but it is generally only recommended to
 * assign value objects to bind variables.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("BINDVARIABLE")
@JsonDeserialize(using = BindVariableDeserializer.class)
@Immutable
public final class BindVariable implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("NAME")
  private final BindName name;
  @JsonProperty("TYPE")
  @JsonSerialize(using = ProvysClassSerializer.class)
  private final Class<?> type;
  // verification in constructor should be sufficient to only have immutable values here
  @SuppressWarnings("Immutable")
  @JsonProperty("VALUE")
  private final @Nullable Serializable value;

  /**
   * Create new bind variable.
   *
   * @param name  is name of new bind variable
   * @param type  is type of new bind variable
   * @param value is default value for bind variable
   * @param <T> is type of bind variable. Used to ensure both match of class and value and to ensure
   *          that bind variables are only based on immutable classes
   */
  public <@ImmutableTypeParameter T extends Serializable> BindVariable(BindName name, Class<T> type,
      @Nullable T value) {
    this.name = name;
    TypeMapImpl.getDefault().validateType(type);
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
   * @param <T> is type of bind variable. Used to ensure both match of class and value and to ensure
   *          that bind variables are only based on immutable classes
   */
  public <@ImmutableTypeParameter T extends Serializable> BindVariable(String name, Class<T> type,
      @Nullable T value) {
    this(BindName.valueOf(name), type, value);
  }

  /**
   * Create new bind variable of unknown type.
   *
   * @param name is name of new bind variable
   */
  public BindVariable(BindName name) {
    this.name = name;
    this.type = TypeMapImpl.getDefault().getAnyType();
    this.value = null;
  }

  /**
   * Create new bind variable of unknown type.
   *
   * @param name is name of new bind variable
   */
  public BindVariable(String name) {
    this(BindName.valueOf(name));
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
  public @Nullable Serializable getValue() {
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

    private static final long serialVersionUID = -2106530400141373951L;
    private @MonotonicNonNull BindName name;
    private @MonotonicNonNull Class<?> type;
    private @Nullable Serializable value;

    SerializationProxy() {
    }

    SerializationProxy(BindVariable bindVariable) {
      this.name = bindVariable.getName();
      this.type = bindVariable.getType();
      this.value = bindVariable.getValue();
    }

    private Object readResolve() throws InvalidObjectException {
      if (name == null) {
        throw new InvalidObjectException("Name not found during BindVariable deserialization");
      }
      if (type == null) {
        throw new InvalidObjectException("Type not found during BindVariable deserialization");
      }
      if (type == TypeMapImpl.getDefault().getAnyType()) {
        if (value != null) {
          throw new InvalidObjectException(
              "Value cannot be specified for bind variable with no type (" + value + ')');
        }
        return new BindVariable(name);
      }
      // we have to rely on the fact that serialized object was immutable - no way to reasonably
      // check it in runtime...
      @SuppressWarnings("Immutable")
      var result = new BindVariable(name, type.asSubclass(Serializable.class), value);
      return result;
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
    int result = name.hashCode();
    result = 31 * result + type.hashCode();
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
