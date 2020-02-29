package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Instance represents multi-line comment.
 */
class ParsedMultiLineComment extends ParsedTokenBase {

  private final String comment;

  ParsedMultiLineComment(int line, int pos, String comment) {
    super(line, pos);
    this.comment = Objects.requireNonNull(comment);
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.COMMENT;
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
    builder.append("/*").append(comment).append("*/");
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
    ParsedMultiLineComment that = (ParsedMultiLineComment) o;
    return Objects.equals(comment, that.comment);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (comment != null ? comment.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedMultiLineComment{"
        + "comment='" + comment + '\''
        + ", " + super.toString() + '}';
  }
}
