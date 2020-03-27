package com.provys.db.sql;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.exception.InternalException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Valid name of sql object. Supports simple name (text starting with letter and containing letters,
 * numbers and characters _, # and $), that is translated to lowercase or quoted name (enclosed in
 * ", containing any printable character, case sensitive). Simple wrapper on String class,
 * immutable. Parse function does normalisation of supplied text, thus equals on sql identifiers is
 * equivalent to two identifiers pointing to the same object (excluding possible path)
 */
@JsonSerialize(using = SimpleNameSerializer.class)
@JsonDeserialize(using = SimpleNameDeserializer.class)
public final class SimpleName extends NamePathBase {

  private static final Pattern PATTERN_ORDINARY = Pattern.compile("([A-Z][A-Z0-9_#$]*)");
  private static final Pattern PATTERN_DELIMITED = Pattern.compile("(\"(?:[^\"]|\"\")*\")");

  /**
   * Parse supplied sql text into identifier. Supports both ordinary and delimited names
   *
   * @param text is supplied sql text
   * @return parsed identifier
   */
  public static SimpleName valueOf(String text) {
    return new SimpleName(text);
  }

  private final String dbName;
  private final boolean delimited;

  private SimpleName(String text) {
    var name = text.trim();
    if (name.isEmpty()) {
      throw new InternalException("Blank text supplied as SQL name");
    }
    if (name.charAt(0) == '"') {
      // delimited identifier
      if (name.contains("\n")) {
        throw new InternalException(
            "Invalid SQL delimited name " + text + "- name cannot contain newline");
      }
      if (!PATTERN_DELIMITED.matcher(name).matches()) {
        throw new IllegalArgumentException(
            "Invalid text supplied for delimited identifier: " + text);
      }
      name = name.substring(1, name.length() - 1).trim().replace("\"\"", "\"");
    } else {
      // ordinary identifier
      name = name.toUpperCase(Locale.ENGLISH);
      if (!PATTERN_ORDINARY.matcher(name).matches()) {
        throw new IllegalArgumentException(
            "Invalid text supplied for ordinary identifier: " + text);
      }
    }
    this.dbName = name;
    // even if name has been supplied as delimited, we want to consider it ordinary if it matches
    // ordinary pattern - otherwise we would consider two equivalent names as different
    delimited = !PATTERN_ORDINARY.matcher(this.dbName).matches();
  }

  /**
   * Database catalogue version of identifier. Ordinary name upper-cased, without delimiters. Can be
   * used for uniqueness checks.
   *
   * @return name as represented in database catalogue
   */
  public String getDbName() {
    return dbName;
  }

  @Override
  public String getText() {
    if (delimited) {
      return '"' + dbName.replace("\"", "\"\"") + '"';
    }
    return dbName.toLowerCase(Locale.ENGLISH);
  }

  @Override
  public boolean isSimple() {
    return true;
  }

  @Override
  public List<SimpleName> getSegments() {
    return List.of(this);
  }

  @Override
  public List<String> getDbNames() {
    return List.of(getDbName());
  }

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this SimpleName
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

    private static final long serialVersionUID = 4943844360741067489L;
    private @Nullable String text;

    SerializationProxy() {
    }

    SerializationProxy(NamePath value) {
      this.text = value.getText();
    }

    private Object readResolve() {
      return SimpleName.valueOf(Objects.requireNonNull(text));
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
    SimpleName that = (SimpleName) o;
    return delimited == that.delimited
        && dbName.equals(that.dbName);
  }

  @Override
  public int hashCode() {
    int result = dbName.hashCode();
    result = 31 * result + (delimited ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return getText();
  }
}
