package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.LiteralT;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

class ParsedLiteral<T> extends ParsedTokenBase implements LiteralT<T> {

    @Nonnull
    private final LiteralT<T> value;

    ParsedLiteral(int line, int pos, LiteralT<T> value) {
        super(line, pos);
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    @Override
    public T getValue() {
        return value.getValue();
    }

    @Nonnull
    @Override
    public SqlTokenType getType() {
        return SqlTokenType.LITERAL;
    }

    @Override
    public SpaceMode spaceBefore() {
        return SpaceMode.NORMAL;
    }

    @Override
    public SpaceMode spaceAfter() {
        return SpaceMode.NORMAL;
    }

    @Override
    public void addSql(CodeBuilder builder) {
        value.addSql(builder);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParsedLiteral<?> that = (ParsedLiteral<?>) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SqlLiteral{" +
                "value=" + value +
                "} " + super.toString();
    }
}
