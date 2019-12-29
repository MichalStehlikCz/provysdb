package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

public class LiteralDtUid extends LiteralBase<DtUid> {

    private static final LiteralDtUid PRIV = new LiteralDtUid(DtUid.PRIV);
    private static final LiteralDtUid ME = new LiteralDtUid(DtUid.ME);

    /**
     * Get literal corresponding to given DtUid value
     *
     * @param value is Uid value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralDtUid of(DtUid value) {
        if (value == DtUid.PRIV) {
            return PRIV;
        } else if (value == DtUid.ME) {
            return ME;
        }
        return new LiteralDtUid(value);
    }

    private LiteralDtUid(DtUid value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append(getValue().toString());
    }
}
