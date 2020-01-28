package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

/**
 * Class represents SQL string literal (VARCHAR2)
 */
class LiteralVarchar extends LiteralBase<String> {

    /**
     * Get literal corresponding to given String value
     *
     * @param value is string value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralVarchar of(String value) {
        return new LiteralVarchar(value);
    }

    private LiteralVarchar(String value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('\'').append(getValue().replace("'", "''")).append('\'');
    }

    @Nonnull
    @Override
    public Class<String> getType() {
        return String.class;
    }
}
