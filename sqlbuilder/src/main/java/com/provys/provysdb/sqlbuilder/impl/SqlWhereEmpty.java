package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlWhere;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

class SqlWhereEmpty implements SqlWhere {

    private static final SqlWhereEmpty INSTANCE = new SqlWhereEmpty();

    static SqlWhereEmpty getInstance() {
        return INSTANCE;
    }

    private SqlWhereEmpty() {}

    @Override
    public void addSql(CodeBuilder builder) {
        // we are expected to insert condition...
        builder.append("(1=1)");
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.emptyList();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}