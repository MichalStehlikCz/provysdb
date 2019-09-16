package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SelectBuilder;
import com.provys.provysdb.sqlbuilder.SqlWhere;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

class SqlWhereSimple implements SqlWhere {

    @Nonnull
    private final String sql;

    SqlWhereSimple(String sql) {
        this.sql = sql;
    }

    @Override
    public void addSql(SelectBuilder selectBuilder, CodeBuilder builder) {
        builder.append('(').append(sql).append(')');
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.emptyList();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
