package com.provys.provysdb.sqlbuilder.elements;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlTableAliasImpl implements SqlTableAlias {

  private static final Pattern PATTERN = Pattern
      .compile("(?:[a-zA-Z][a-zA-Z0-9_#$]*)|(?:\"[^\"]*\")|(?:<<[0-9]+>>)");

  private static String validate(String aliasText) {
    if (aliasText.isBlank()) {
      throw new InternalException("Blank text supplied as SQL table alias");
    }
    if (!PATTERN.matcher(aliasText).matches()) {
      throw new InternalException(
          "Supplied text is not valid SQL table alias - '" + aliasText + '\'');
    }
    if ((aliasText.charAt(0) == '"') || (aliasText.charAt(0) == '<')) {
      return aliasText;
    }
    return aliasText.toLowerCase(Locale.ENGLISH);
  }

  private final String alias;

  SqlTableAliasImpl(String alias) {
    this.alias = validate(alias);
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlTableAliasImpl that = (SqlTableAliasImpl) o;
    return Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    return alias != null ? alias.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "SqlTableAliasImpl{"
        + "alias='" + alias + '\''
        + '}';
  }
}
