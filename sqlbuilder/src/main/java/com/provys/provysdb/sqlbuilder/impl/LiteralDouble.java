package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

public class LiteralDouble extends LiteralNumber<Double> {

    /**
     * Get literal corresponding to given Double value
     *
     * @param value is double value this literal represents
     */
    @Nonnull
    public static LiteralDouble of(double value) {
        return new LiteralDouble(value);
    }

    private LiteralDouble(Double value) {
        super(value);
    }
}
