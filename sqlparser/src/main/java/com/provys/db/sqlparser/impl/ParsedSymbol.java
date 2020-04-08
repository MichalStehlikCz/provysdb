package com.provys.db.sqlparser.impl;

import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.sqlparser.SqlSymbol;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Instance represents valid SQL symbol.
 */
final class ParsedSymbol extends ParsedTokenBase implements SqlParsedTokenSymbol {

  private final SqlSymbol symbol;

  ParsedSymbol(int line, int pos, SqlSymbol symbol) {
    super(line, pos);
    this.symbol = Objects.requireNonNull(symbol);
  }

  @Override
  public SqlSymbol getSymbol() {
    return symbol;
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.SYMBOL;
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
    consumer.symbol(symbol.getSymbol());
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
    ParsedSymbol that = (ParsedSymbol) o;
    return symbol == that.symbol;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + symbol.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedSymbol{"
        + "symbol=" + symbol
        + ", " + super.toString() + '}';
  }
}
