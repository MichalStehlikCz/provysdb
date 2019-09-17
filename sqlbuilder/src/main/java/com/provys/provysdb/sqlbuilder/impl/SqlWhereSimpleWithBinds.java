package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlWhere;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SqlWhereSimpleWithBinds implements SqlWhere {

    @Nonnull
    private final String sql;
    @Nonnull
    private final List<BindVariable> binds;

    SqlWhereSimpleWithBinds(String sql, Collection<BindVariable> binds) {
        this.sql = sql;
        this.binds = new ArrayList<>(binds);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('(').append(sql).append(')');
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.unmodifiableList(binds);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
