package com.provys.db.sqlparser.impl;

import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.SimpleName;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents name or keyword. Ordinary identifier is one that is not surrounded by double quotation
 * marks
 */
final class ParsedName extends ParsedTokenBase {

  private final SimpleName name;

  ParsedName(int line, int pos, SimpleName name) {
    super(line, pos);
    this.name = name;
  }

  ParsedName(int line, int pos, String name) {
    this(line, pos, SimpleName.valueOf(name));
  }

  /**
   * Value of field name.
   *
   * @return value of field name
   */
  public SimpleName getName() {
    return name;
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.NAME;
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
    consumer.name(name);
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
    ParsedName that = (ParsedName) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedName{"
        + "name=" + name
        + ", " + super.toString() + '}';
  }
}
