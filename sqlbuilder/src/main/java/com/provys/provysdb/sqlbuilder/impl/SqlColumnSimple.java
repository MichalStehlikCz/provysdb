package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

class SqlColumnSimple extends SqlColumnBase {

    @Nullable
    private final SqlTableAlias tableAlias;
    @Nonnull
    private final SqlName column;

    SqlColumnSimple(@Nullable SqlTableAlias tableAlias, SqlName column, @Nullable SqlName alias) {
        super(alias);
        this.tableAlias = tableAlias;
        this.column = Objects.requireNonNull(column);
    }

    @Override
    public void addSql(SelectBuilder selectBuilder, CodeBuilder builder) {
        if (tableAlias != null) {
            builder.append(tableAlias.getAlias(selectBuilder)).append('.');
        }
        builder.append(column);
        getAlias().ifPresent(alias -> builder.append(' ').append(alias));
        builder.appendLine();
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlColumnSimple that = (SqlColumnSimple) o;

        if (tableAlias != null ? !tableAlias.equals(that.tableAlias) : that.tableAlias != null) return false;
        return column.equals(that.column);
    }

    @Override
    public int hashCode() {
        int result = tableAlias != null ? tableAlias.hashCode() : 0;
        result = 31 * result + column.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SqlColumnSimple{" +
                "tableAlias=" + tableAlias +
                ", column=" + column +
                ", alias=" + getAlias().orElse(null) +
                '}';
    }
}
