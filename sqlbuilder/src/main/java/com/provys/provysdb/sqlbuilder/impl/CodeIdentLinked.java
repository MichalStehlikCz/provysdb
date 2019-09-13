package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

/**
 * Enables ident to use prefix from previous ident and mark its first line as used
 */
public class CodeIdentLinked implements CodeIdent {
    @Nonnull
    private final CodeIdent ident;
    @Nonnull
    private final CodeIdent parent;

    CodeIdentLinked(CodeIdent ident, CodeIdent parent) {
        this.ident = ident;
        this.parent = parent;
    }


    @Nonnull
    @Override
    public String get() {
        return parent.get() + ident.get();
    }

    @Override
    public void use(StringBuilder builder) {
        parent.use(builder);
        ident.use(builder);
    }
}
