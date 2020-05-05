package com.provys.db.sqlparser.impl;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.sqlparser.SqlKeyword;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
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
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public SqlToken mapBinds(BindMap bindMap) {
    return this;
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.keyword(keyword.toString());
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
    result = 31 * result + keyword.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedKeyword{"
        + "keyword=" + keyword
        + ", " + super.toString() + '}';
  }
}
