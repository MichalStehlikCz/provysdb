package com.provys.provysdb.sqlbuilder.impl;

import com.provys.common.datatype.DtDateTime;
import com.provys.provysdb.sqlbuilder.CodeBuilder;

import javax.annotation.Nonnull;

/**
 * Class represents SQL datetime literal (maps to DATE)
 */
public class LiteralDateTime extends LiteralBase<DtDateTime> {

    /**
     * Create date literal from supplied date value
     *
     * @param value is supplied date value
     * @return date literal, representing supplied value
     */
    @Nonnull
    public static LiteralDateTime of(DtDateTime value) {
        return new LiteralDateTime(value);
    }

    private LiteralDateTime(DtDateTime value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("TO_DATE('").append(getValue().toIso()).append("', 'YYYY-MM-DD\"T\"HH24:MI:SS')");
    }
}
