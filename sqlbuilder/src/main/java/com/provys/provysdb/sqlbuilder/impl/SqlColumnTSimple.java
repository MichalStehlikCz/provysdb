package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlbuilder.SqlTableAlias;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SqlColumnTSimple<T> extends SqlColumnSimple implements SqlColumnT<T> {

    @Nonnull
    private final Class<T> type;

    SqlColumnTSimple(@Nullable SqlTableAlias tableAlias, SqlIdentifier column, @Nullable SqlIdentifier alias,
                     Class<T> type) {
        super(tableAlias, column, alias);
        this.type = type;
    }

    @Override
    @Nonnull
    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SqlColumnTSimple<?> that = (SqlColumnTSimple<?>) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
