package com.provys.provysdb.sqlbuilder.elements;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindName;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default implementation of bind name. Provides validation and normalisation of name of bind
 * variable (as everything else in SQL, bind names are case insensitive. Bind name mus be string
 * that starts with letter and contains only letters, numbers and underscore.
 */
final class BindNameImpl implements BindName {

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
  static BindNameImpl of(String name) {
    return new BindNameImpl(name);
  }

  private final String name;

  private BindNameImpl(String name) {
    this.name = validateName(name);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BindNameImpl)) {
      return false;
    }
    BindNameImpl bindName = (BindNameImpl) o;
    return Objects.equals(name, bindName.name);
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "BindNameImpl{"
        + "name='" + name + '\''
        + '}';
  }
}
