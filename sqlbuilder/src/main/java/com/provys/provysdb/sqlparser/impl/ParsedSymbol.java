package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlSymbol;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Instance represents valid SQL symbol
 */
class ParsedSymbol extends ParsedTokenBase implements SqlParsedTokenSymbol {

    @Nonnull
    private final SqlSymbol symbol;

    ParsedSymbol(int line, int pos, SqlSymbol symbol) {
        super(line, pos);
        this.symbol = Objects.requireNonNull(symbol);
    }

    @Nonnull
    @Override
    public SqlTokenType getType() {
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
    @Nonnull
    public SqlSymbol getSymbol() {
        return symbol;
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append(symbol.getSymbol());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParsedSymbol sqlSymbol = (ParsedSymbol) o;

        return symbol.equals(sqlSymbol.symbol);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SqlSymbol{" +
                "symbol='" + symbol.getSymbol() + '\'' +
                "} " + super.toString();
    }
}
