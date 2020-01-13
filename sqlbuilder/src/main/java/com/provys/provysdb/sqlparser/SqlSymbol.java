package com.provys.provysdb.sqlparser;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Enum lists strings that can be used as symbols in SQL language (or - more exactly - Oracle SQL dialect)
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
    private final boolean comparison;

    SqlSymbol(String symbol, boolean comparison) {
        this.symbol = Objects.requireNonNull(symbol);
        this.comparison = comparison;
    }

    @Nonnull
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return value of field comparison
     */
    public boolean isComparison() {
        return comparison;
    }
}
