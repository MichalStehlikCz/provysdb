package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

class LiteralLong extends LiteralNumber<Long> {

    /**
     * Get literal corresponding to given Long value
     *
     * @param value is long value this literal represents
     */
    @Nonnull
    static LiteralLong of(long value) {
        return new LiteralLong(value);
    }

    private LiteralLong(Long value) {
        super(value);
    }
}
