package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

class LiteralInt extends LiteralNumber<Integer> {

    /**
     * Get literal corresponding to given Integer value
     *
     * @param value is integer value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralInt of(int value) {
        return new LiteralInt(value);
    }

    private LiteralInt(Integer value) {
        super(value);
    }

    @Nonnull
    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
}
