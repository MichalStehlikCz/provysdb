package com.provys.provysdb.sqlbuilder.elements;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.Identifier;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Implements support for SQL strings that can act as name. Simple wrapper on String class,
 * non-mutable. Does normalisation of supplied text, thus equals on sql identifiers is equivalent to
 * two identifiers pointing to the same object
 */
final class IdentifierImpl implements Identifier {

  private static final Pattern PATTERN_ORDINARY = Pattern.compile("([A-Z][A-Z0-9_#$]*)");
  private static final Pattern PATTERN_DELIMITED = Pattern.compile("(\"(?:[^\"]|\"\")*\")");

  /**
   * Parse supplied sql text into identifier. Supports both ordinary and delimited names
   *
   * @param text is supplied sql text
   * @return parsed identifier
   */
  static IdentifierImpl parse(String text) {
    var result = text.trim();
    if (result.charAt(0) == '"') {
      // delimited identifier
      if (!PATTERN_DELIMITED.matcher(result).matches()) {
        throw new IllegalArgumentException(
            "Invalid text supplied for delimited identifier: " + text);
      }
      result = result.substring(1, result.length() - 1).trim().replace("\"\"", "\"");
    } else {
      // ordinary identifier
      result = result.toUpperCase(Locale.ENGLISH);
      if (!PATTERN_ORDINARY.matcher(result).matches()) {
        throw new IllegalArgumentException(
            "Invalid text supplied for ordinary identifier: " + text);
      }
    }
    return new IdentifierImpl(result);
  }

  private static String validate(String name) {
    var result = name.trim();
    if (result.isEmpty()) {
      throw new InternalException("Blank text supplied as SQL name");
    }
    if (result.contains("\n")) {
      throw new InternalException("Invalid SQL delimited name - name cannot contain newline");
    }
    return result;
  }

  private final String dbName;
  private final boolean delimited;

  private IdentifierImpl(String dbName) {
    this.dbName = validate(dbName);
    delimited = !PATTERN_ORDINARY.matcher(this.dbName).matches();
  }

  @Override
  public String getText() {
    if (delimited) {
      return '"' + dbName.replace("\"", "\"\"") + '"';
    }
    return dbName.toLowerCase(Locale.ENGLISH);
  }

  @Override
  public String getDbName() {
    return dbName;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdentifierImpl that = (IdentifierImpl) o;
    return delimited == that.delimited
        && Objects.equals(dbName, that.dbName);
  }

  @Override
  public int hashCode() {
    int result = dbName != null ? dbName.hashCode() : 0;
    result = 31 * result + (delimited ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlIdentifierImpl{"
        + "dbName='" + dbName + '\''
        + ", delimited=" + delimited
        + '}';
  }
}
