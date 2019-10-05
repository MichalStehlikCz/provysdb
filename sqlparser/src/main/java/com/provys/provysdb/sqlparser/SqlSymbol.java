package com.provys.provysdb.sqlparser;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Instance represents valid SQL symbol
 */
public class SqlSymbol extends SqlTokenBase {

    public static final Set<String> SYMBOLS;
    static {
        SYMBOLS = new HashSet<>();
        SYMBOLS.add("=>");
        SYMBOLS.add(":=");
        SYMBOLS.add("!=");
        SYMBOLS.add("<=");
        SYMBOLS.add(">=");
        SYMBOLS.add("<>");
        SYMBOLS.add("(");
        SYMBOLS.add(")");
        SYMBOLS.add(",");
        SYMBOLS.add("+");
        SYMBOLS.add("-");
        SYMBOLS.add("/");
        SYMBOLS.add("*");
        SYMBOLS.add(";");
        SYMBOLS.add(".");
        SYMBOLS.add("%");
        SYMBOLS.add("=");
        SYMBOLS.add(">");
        SYMBOLS.add("<");
    }

    @Nonnull
    private final String symbol;

    SqlSymbol(int line, int pos, String symbol) {
        super(line, pos);
        this.symbol = Objects.requireNonNull(symbol);
    }

    @Nonnull
    @Override
    public SqlTokenType getType() {
        return SqlTokenType.SYMBOL;
    }

    @Override
    public void append(CodeBuilder builder) {
        builder.append(symbol);
    }
}
