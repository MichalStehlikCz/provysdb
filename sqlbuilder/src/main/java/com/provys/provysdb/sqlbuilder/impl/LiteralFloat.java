package com.provys.provysdb.sqlbuilder.impl;

import javax.annotation.Nonnull;

class LiteralFloat extends LiteralNumber<Float> {

    /**
     * Get literal corresponding to given Float value
     *
     * @param value is float value this literal represents
     */
    @Nonnull
    static LiteralFloat of(float value) {
        return new LiteralFloat(value);
    }

    private LiteralFloat(Float value) {
        super(value);
    }
}
