package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;

abstract class LiteralNumber<T extends Number> extends LiteralBase<T> {

    LiteralNumber(T value) {
        super(value);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append(getValue().toString());
    }
}
