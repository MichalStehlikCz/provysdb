package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

/**
 * Simple ident (all lines with the same prefix)
 */
public class CodeIdentSimple implements CodeIdent {

    @Nonnull
    private final String ident;

    CodeIdentSimple(String ident) {
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
}
