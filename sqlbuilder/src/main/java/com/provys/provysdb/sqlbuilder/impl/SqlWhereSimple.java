package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlWhere;

import javax.annotation.Nonnull;

class SqlWhereSimple implements SqlWhere {

    @Nonnull
    private final String sql;

    SqlWhereSimple(String sql) {
        this.sql = sql;
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('(').append(sql).append(')');
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
