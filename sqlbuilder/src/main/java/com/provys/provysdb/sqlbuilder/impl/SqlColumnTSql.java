package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SqlColumnTSql<T> extends SqlColumnSql implements SqlColumnT<T> {

    @Nonnull
    private final Class<T> type;

    SqlColumnTSql(String sql, @Nullable SqlIdentifier alias, Class<T> type) {
        super(sql, alias);
        this.type = type;
    }

    SqlColumnTSql(String sql, @Nullable SqlIdentifier alias, List<BindName> binds, Class<T> type) {
        super(sql, alias, binds);
        this.type = type;
    }

    @Nonnull
    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlColumnTSql<?> that = (SqlColumnTSql<?>) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
