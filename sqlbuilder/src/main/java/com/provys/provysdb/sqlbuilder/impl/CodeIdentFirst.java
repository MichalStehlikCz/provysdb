package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

class CodeIdentFirst implements CodeIdent {

    @Nonnull
    private final String firstIdent;
    @Nonnull
    private final String ident;
    private boolean firstActive;

    CodeIdentFirst(String firstIdent, String ident) {
        this.firstIdent = firstIdent;
        this.ident = ident;
        this.firstActive = true;
    }

    @Nonnull
    @Override
    public String get() {
        return firstActive ? firstIdent : ident;
    }

    @Override
    public void use(StringBuilder builder) {
        builder.append(get());
        firstActive = false;
    }
}
