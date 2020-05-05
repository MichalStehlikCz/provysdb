package com.provys.db.sqlparser.impl;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Instance represents multi-line comment.
 */
@Immutable
final class ParsedMultiLineComment extends ParsedTokenBase {

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
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public SqlToken mapBinds(BindMap bindMap) {
    return this;
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.longComment(comment);
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
    return comment.equals(that.comment);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + comment.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedMultiLineComment{"
        + "comment='" + comment + '\''
        + ", " + super.toString() + '}';
  }
}
