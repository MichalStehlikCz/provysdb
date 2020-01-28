package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

class LiteralNVarchar extends LiteralBase<String> {

    /**
     * Get NVARCHAR2 literal corresponding to given String value
     *
     * @param value is string value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralNVarchar of(String value) {
        return new LiteralNVarchar(value);
    }

    private LiteralNVarchar(String value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("N'").append(getValue().replace("'", "''")).append('\'');
    }

    @Nonnull
    @Override
    public Class<String> getType() {
        return String.class;
    }
}
