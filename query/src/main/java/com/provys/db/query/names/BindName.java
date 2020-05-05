package com.provys.db.query.names;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Bind name represents name of bind variable. Provides validation and normalisation of name of bind
 * variable - as everything else in SQL, bind names are case insensitive. Bind name must be string
 * that starts with letter and contains only letters, numbers and underscore.
 */
@JsonSerialize(using = BindNameSerializer.class)
@JsonDeserialize(using = BindNameDeserializer.class)
@Immutable
public final class BindName implements Serializable {

  private static final Pattern NAME_PATTERN = Pattern.compile("([A-Z][A-Z0-9_]*)");

  private static String validateName(String name) {
    var result = name.trim().toUpperCase(Locale.ENGLISH);
    if (!NAME_PATTERN.matcher(result).matches()) {
      throw new InternalException("Invalid bind name " + name);
    }
    return result;
  }

  /**
   * Create bind name based on supplied name.
   *
   * @param name is name of bind
   * @return bind name based on supplied string
   */
  public static BindName valueOf(String name) {
    return new BindName(name);
  }

  private final String name;

  private BindName(String name) {
    this.name = validateName(name);
  }

  /**
   * Name this BindName represents.
   *
   * @return name this bind name represents
   */
  public String getName() {
    return name;
  }

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this BindName
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

    private static final long serialVersionUID = -8330342720996564435L;
    private @Nullable String name;

    SerializationProxy() {
    }

    SerializationProxy(BindName value) {
      this.name = value.getName();
    }

    private Object readResolve() throws InvalidObjectException {
      if (name == null) {
        throw new InvalidObjectException("Name not found during BindName deserialization");
      }
      return valueOf(name);
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BindName)) {
      return false;
    }
    BindName bindName = (BindName) o;
    return Objects.equals(name, bindName.name);
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "BindName{"
        + "name='" + name + '\''
        + '}';
  }
}
