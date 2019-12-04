package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

class LiteralByte extends LiteralNumber<Byte> {

    /**
     * Get literal corresponding to given Byte value
     *
     * @param value is byte value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralByte of(byte value) {
        return new LiteralByte(value);
    }

    private LiteralByte(Byte value) {
        super(value);
    }
}
