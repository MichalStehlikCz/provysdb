package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

class SqlColumnSimple extends SqlColumnBase {

    @Nullable
    private final SqlTableAlias tableAlias;
    @Nonnull
    private final SqlIdentifier column;

    SqlColumnSimple(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, @Nullable SqlIdentifier alias) {
        super(alias);
        this.tableAlias = tableAlias;
        this.column = Objects.requireNonNull(column);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        if (tableAlias != null) {
            builder.append(tableAlias.getAlias()).append('.');
        }
        builder.append(column);
        getAlias().ifPresent(alias -> builder.append(' ').append(alias));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlColumnSimple that = (SqlColumnSimple) o;

        return Objects.equals(tableAlias, that.tableAlias) &&
                column.equals(that.column);
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
