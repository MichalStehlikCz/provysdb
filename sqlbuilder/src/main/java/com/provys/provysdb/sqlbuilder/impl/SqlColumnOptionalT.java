package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

class SqlColumnOptionalT<T> implements SqlColumnT<Optional<T>> {

    @Nonnull
    private final SqlColumn column;
    @Nonnull
    private final Class<T> type;

    SqlColumnOptionalT(SqlColumn column, Class<T> type) {
        this.column = Objects.requireNonNull(column);
        this.type = Objects.requireNonNull(type);
    }

    @Override
    @Nonnull
    public Optional<SqlIdentifier> getAlias() {
        return column.getAlias();
    }

    @Override
    public void addSql(CodeBuilder builder) {
        column.addSql(builder);
    }

    @Override
    public SqlTypeAdapter<Optional<T>> getAdapter(SqlTypeMap typeMap) {
        return typeMap.getOptionalAdapter(type);
    }
}
