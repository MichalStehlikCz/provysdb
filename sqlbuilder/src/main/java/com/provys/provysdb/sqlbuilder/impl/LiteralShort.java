package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

class LiteralShort extends LiteralNumber<Short> {

    /**
     * Get literal corresponding to given Short value
     *
     * @param value is short value this literal represents
     */
    @Nonnull
    static LiteralShort of(short value) {
        return new LiteralShort(value);
    }

    private LiteralShort(Short value) {
        super(value);
    }
}
