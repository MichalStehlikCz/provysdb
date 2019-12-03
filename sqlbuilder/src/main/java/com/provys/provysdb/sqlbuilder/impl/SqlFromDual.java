package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

class SqlFromDual extends SqlFromBase {

    private static final SqlFromDual INSTANCE = new SqlFromDual();

    static SqlFromDual getInstance() {
        return INSTANCE;
    }

    private SqlFromDual() {
        super(new SqlTableAliasImpl("dual"));
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("dual");
    }
}
