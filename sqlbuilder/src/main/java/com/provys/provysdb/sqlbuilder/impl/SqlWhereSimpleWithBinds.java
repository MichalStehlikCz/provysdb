package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlWhere;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqlWhereSimpleWithBinds implements SqlWhere {

    @Nonnull
    private final String sql;
    @Nonnull
    private final List<BindName> binds;

    SqlWhereSimpleWithBinds(String sql, Collection<BindName> binds) {
        this.sql = sql;
        this.binds = new ArrayList<>(binds);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('(').append(sql).append(')');
        builder.addBinds(binds);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
