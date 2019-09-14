package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

/**
 * Simple ident (all lines with the same prefix)
 */
class CodeIdentSimple implements CodeIdent {

    @Nonnull
    static CodeIdentSimple of(String ident) {
        return new CodeIdentSimple(ident);
    }

    @Nonnull
    private final String ident;

    private CodeIdentSimple(String ident) {
        this.ident = ident;
    }


    @Nonnull
    @Override
    public String get() {
        return ident;
    }

    @Override
    public void use(StringBuilder builder) {
        builder.append(get());
    }

    @Nonnull
    @Override
    public CodeIdent copy() {
        /* non-mutable, thus can return itself */
        return this;
    }
}
