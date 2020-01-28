package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

class LiteralDouble extends LiteralNumber<Double> {

    /**
     * Get literal corresponding to given Double value
     *
     * @param value is double value this literal represents
     * @return new literal, representing supplied value
     */
    @Nonnull
    static LiteralDouble of(double value) {
        return new LiteralDouble(value);
    }

    private LiteralDouble(Double value) {
        super(value);
    }

    @Nonnull
    @Override
    public Class<Double> getType() {
        return Double.class;
    }
}
