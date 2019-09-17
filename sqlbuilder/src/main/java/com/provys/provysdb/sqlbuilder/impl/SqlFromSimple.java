package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Used to represent element in from clause, built on table or view
 */
class SqlFromSimple extends SqlFromBase {

    @Nonnull
    private final SqlName tableName;

    SqlFromSimple(SqlName tableName, SqlTableAlias alias) {
        super(alias);
        this.tableName = Objects.requireNonNull(tableName);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append(tableName).append(' ').append(getAlias());
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.emptyList();
    }
}
