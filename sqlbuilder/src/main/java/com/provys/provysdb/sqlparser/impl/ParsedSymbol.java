package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlSymbol;
import com.provys.provysdb.sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Instance represents valid SQL symbol.
 */
class ParsedSymbol extends ParsedTokenBase implements SqlParsedTokenSymbol {

  private final SqlSymbol symbol;

  ParsedSymbol(int line, int pos, SqlSymbol symbol) {
    super(line, pos);
    this.symbol = Objects.requireNonNull(symbol);
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.SYMBOL;
  }

  @Override
  public SpaceMode spaceBefore() {
    if (symbol == SqlSymbol.PARAM_VALUE) {
      return SpaceMode.FORCE;
    }
    return SpaceMode.NONE;
  }

  @Override
  public SpaceMode spaceAfter() {
    if ((symbol == SqlSymbol.PARAM_VALUE) || (symbol == SqlSymbol.COMMA)) {
      return SpaceMode.FORCE;
    }
    return SpaceMode.NONE;
  }

  @Override
  public SqlSymbol getSymbol() {
    return symbol;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append(symbol.getSymbol());
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
    result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedSymbol{"
        + "symbol=" + symbol
        + ", " + super.toString() + '}';
  }
}
