package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtBoolean;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

class LiteralBoolean extends LiteralBase<Boolean> {

    private static final LiteralBoolean TRUE = new LiteralBoolean(true);
    private static final LiteralBoolean FALSE = new LiteralBoolean(false);

    static LiteralBoolean of(boolean value) {
        return value ? TRUE : FALSE;
    }

    LiteralBoolean(Boolean value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('\'').append(DtBoolean.toProvysDb(getValue())).append('\'');
    }

    @Nonnull
    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }
}
