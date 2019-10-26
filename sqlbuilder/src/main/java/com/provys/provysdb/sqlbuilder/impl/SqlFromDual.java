package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;

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
