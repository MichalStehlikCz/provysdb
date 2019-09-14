package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

/**
 * This ident does exactly nothing when used - it can be used as default ident
 */
class CodeIdentVoid implements CodeIdent {

    @Nonnull
    private static final CodeIdentVoid INSTANCE = new CodeIdentVoid();

    /**
     * @return instance of {@code CodeIdentVoid}
     */
    @Nonnull
    static CodeIdentVoid getInstance() {
        return INSTANCE;
    }

    /**
     * Static getInstance method should be used instead of constructor
     */
    private CodeIdentVoid() {}

    @Nonnull
    @Override
    public String get() {
        return "";
    }

    @Override
    public void use(StringBuilder builder) {
        // does nothing and it is exactly what we want
    }

    @Nonnull
    @Override
    public CodeIdent copy() {
        // is non-mutable thus can return self
        return this;
    }
}
