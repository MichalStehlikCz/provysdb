package com.provys.db.sqlparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Enum lists strings that can be used as symbols in SQL language (or - more exactly - Oracle SQL
 * dialect).
 */
public enum SqlSymbol {

  PARAM_VALUE("=>", false),
  ASSIGNMENT(":=", false),
  NOT_EQUAL("!=", true),
  LESS_OR_EQUAL("<=", true),
  GREATER_OR_EQUAL(">=", true),
  INEQUAL("<>", true),
  OPENING_BRACKET("(", false),
  CLOSING_BRACKET(")", false),
  COMMA(",", false),
  PLUS("+", false),
  MINUS("-", false),
  SLASH("/", false),
  STAR("*", false),
  SEMICOLON(";", false),
  DOT(".", false),
  PERCENTAGE("%", false),
  EQUAL("=", true),
  GRATER_THAN(">", true),
  LESS_THAN("<", true);

  private static final Map<String, SqlSymbol> valueBySymbol = new HashMap<>(20);

  static {
    for (var value : values()) {
      valueBySymbol.put(value.symbol, value);
    }
  }

  /**
   * Retrieve value corresponding to given symbol.
   *
   * @param symbol is symbol we look up
   * @return value corresponding to supplied symbol
   */
  public static Optional<SqlSymbol> getBySymbol(String symbol) {
    return Optional.ofNullable(valueBySymbol.get(symbol));
  }

  private final String symbol;
  private final boolean comparison;

  SqlSymbol(String symbol, boolean comparison) {
    this.symbol = Objects.requireNonNull(symbol);
    this.comparison = comparison;
  }

  /**
   * String representation of symbol.
   *
   * @return string representing this symbol
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Indication if symbol is comparison.
   *
   * @return value of field comparison
   */
  public boolean isComparison() {
    return comparison;
  }
}
