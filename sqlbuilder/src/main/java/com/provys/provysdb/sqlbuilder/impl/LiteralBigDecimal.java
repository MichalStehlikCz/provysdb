package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

class LiteralBigDecimal extends LiteralNumber<BigDecimal> {

    /**
     * Get literal corresponding to given BigDecimal value
     *
     * @param value is value this literal represents
     */
    @Nonnull
    static LiteralBigDecimal of(BigDecimal value) {
        return new LiteralBigDecimal(value);
    }

    private LiteralBigDecimal(BigDecimal value) {
        super(value);
    }
}
