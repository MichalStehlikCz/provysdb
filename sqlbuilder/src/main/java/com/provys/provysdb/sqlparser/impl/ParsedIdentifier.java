package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlFactory;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Represents name or keyword. Ordinary identifier is one that is not surrounded by double quotation marks
 */
class ParsedIdentifier extends ParsedTokenBase implements SqlIdentifier {

    @Nonnull
    private final SqlIdentifier identifier;

    ParsedIdentifier(int line, int pos, String name) {
        super(line, pos);
        this.identifier = SqlFactory.name(name);
    }

    ParsedIdentifier(int line, int pos, SqlIdentifier identifier) {
        super(line, pos);
        this.identifier = Objects.requireNonNull(identifier);
    }

    @Nonnull
    @Override
    public SqlTokenType getTokenType() {
        return SqlTokenType.NAME;
    }

    @Override
    public SpaceMode spaceBefore() {
        return SpaceMode.NORMAL;
    }

    @Override
    public SpaceMode spaceAfter() {
        return SpaceMode.NORMAL;
    }

    @Nonnull
    @Override
    public String getText() {
        return identifier.getText();
    }

    @Nonnull
    @Override
    public String getDbName() {
        return identifier.getDbName();
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append(identifier.getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParsedIdentifier parsedIdentifier = (ParsedIdentifier) o;

        return identifier.equals(parsedIdentifier.identifier);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + identifier.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SqlName{" +
                "identifier='" + identifier + '\'' +
                "} " + super.toString();
    }
}
