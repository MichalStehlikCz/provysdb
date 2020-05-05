package com.provys.db.sqlparser.impl;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents single line comment in Sql code.
 */
@Immutable
final class ParsedSingleLineComment extends ParsedTokenBase {

  private final String text;

  ParsedSingleLineComment(int line, int pos, String text) {
    super(line, pos);
    this.text = text.stripTrailing();
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
    consumer.simpleComment(text);
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
    ParsedSingleLineComment that = (ParsedSingleLineComment) o;
    return text.equals(that.text);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + text.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedSingleLineComment{"
        + "text='" + text + '\''
        + ", " + super.toString() + '}';
  }
}
