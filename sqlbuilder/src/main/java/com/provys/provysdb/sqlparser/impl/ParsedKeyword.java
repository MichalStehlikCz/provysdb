package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlKeyword;
import com.provys.provysdb.sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class ParsedKeyword extends ParsedTokenBase {

  private final SqlKeyword keyword;

  ParsedKeyword(int line, int pos, SqlKeyword keyword) {
    super(line, pos);
    this.keyword = Objects.requireNonNull(keyword);
  }


  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.KEYWORD;
  }

  /**
   * Value of field keyword.
   *
   * @return value of field keyword
   */
  SqlKeyword getKeyword() {
    return keyword;
  }

  @Override
  public SpaceMode spaceBefore() {
    return SpaceMode.FORCE;
  }

  @Override
  public SpaceMode spaceAfter() {
    return SpaceMode.FORCE;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append(keyword.toString());
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
    ParsedKeyword that = (ParsedKeyword) o;
    return keyword == that.keyword;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedKeyword{"
        + "keyword=" + keyword
        + ", " + super.toString() + '}';
  }
}
