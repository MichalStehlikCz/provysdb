package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDate;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

/**
 * Class represents SQL date literal (maps to DATE)
 */
public class LiteralDate extends LiteralBase<DtDate> {

    /**
     * Create date literal from supplied date value
     *
     * @param value is supplied date value
     * @return date literal, representing supplied value
     */
    @Nonnull
    public static LiteralDate of(DtDate value) {
        return new LiteralDate(value);
    }

    private LiteralDate(DtDate value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("DATE'").append(getValue().toIso()).append("'");
    }
}
