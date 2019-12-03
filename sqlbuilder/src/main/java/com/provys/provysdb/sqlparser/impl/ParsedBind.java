package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.impl.BindNameImpl;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;

import javax.annotation.Nonnull;
import java.util.Objects;

class ParsedBind extends ParsedTokenBase implements BindName {

    private final BindName name;

    ParsedBind(int line, int pos, String name) {
        super(line, pos);
        this.name = new BindNameImpl(name);
    }

    @Nonnull
    @Override
    public String getName() {
        return name.getName();
    }

    @Nonnull
    @Override
    public BindValue withValue(Object value) {
        return name.withValue(value);
    }

    @Nonnull
    @Override
    public BindName combine(BindName other) {
        return name.combine(other);
    }

    @Nonnull
    @Override
    public SqlTokenType getType() {
        return SqlTokenType.BIND;
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
        name.addSql(builder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ParsedBind that = (ParsedBind) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParsedBind{" +
                "name=" + name +
                "} " + super.toString();
    }
}
