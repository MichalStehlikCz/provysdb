package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;
import java.math.BigInteger;

class LiteralBigInteger extends LiteralNumber<BigInteger> {

    /**
     * Get literal corresponding to given Integer value
     *
     * @param value is integer value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralBigInteger of(BigInteger value) {
        return new LiteralBigInteger(value);
    }

    private LiteralBigInteger(BigInteger value) {
        super(value);
    }

    @Nonnull
    @Override
    public Class<BigInteger> getType() {
        return BigInteger.class;
    }
}
