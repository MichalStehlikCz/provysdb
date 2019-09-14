package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;

/**
 * Enables ident to use prefix from previous ident and mark its first line as used. Note that linked ident itself is
 * not stateful, but underlying idents might be stateful, meaning linked ident itself gets stateful
 */
class CodeIdentLinked implements CodeIdent {

    /**
     * Create new linked ident
     *
     * @param ident is main ident (appended)
     * @param linkedIdent is previous ident, used as prefix
     * @return linked ident with ident linked to linkedIdent
     */
    @Nonnull
    static CodeIdentLinked of(CodeIdent ident, CodeIdent linkedIdent) {
        return new CodeIdentLinked(ident, linkedIdent);
    }

    @Nonnull
    private final CodeIdent ident;
    @Nonnull
    private final CodeIdent parent;

    private CodeIdentLinked(CodeIdent ident, CodeIdent parent) {
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

    @Nonnull
    @Override
    public CodeIdent copy() {
        return CodeIdentLinked.of(ident.copy(), parent.copy());
    }
}
