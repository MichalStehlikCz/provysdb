package com.provys.provysdb.sqlparser;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public enum SqlSymbol {

    PARAM_VALUE("=>"),
    ASSIGNMENT(":="),
    NOT_EQUAL("!="),
    LESS_OR_EQUAL("<="),
    GREATER_OR_EQUAL(">="),
    INEQUAL("<>"),
    OPENING_BRACKET("("),
    CLOSING_BRACKET(")"),
    COMMA(","),
    PLUS("+"),
    MINUS("-"),
    SLASH("/"),
    STAR("*"),
    SEMICOLON(";"),
    DOT("."),
    PERCENTAGE("%"),
    EQUAL("="),
    GRATER_THAN(">"),
    LESS_THAN("<");

    @Nonnull
    private static final Map<String, SqlSymbol> valueBySymbol = new HashMap<>();
    static {
        for (var value : values()) {
            valueBySymbol.put(value.symbol, value);
        }
    }

    @Nonnull
    public static Optional<SqlSymbol> getBySymbol(String symbol) {
        return Optional.ofNullable(valueBySymbol.get(symbol));
    }

    @Nonnull
    private final String symbol;

    SqlSymbol(String symbol) {
        this.symbol = Objects.requireNonNull(symbol);
    }

    @Nonnull
    public String getSymbol() {
        return symbol;
    }
}
