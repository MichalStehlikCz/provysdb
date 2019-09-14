package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeIdent;
import com.provys.provysdb.sqlbuilder.CodeIdentBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class CodeIdentBuilderImpl implements CodeIdentBuilder {

    @Nullable
    private String ident = null;
    @Nullable
    private String firstIdent = null;
    @Nullable
    private CodeIdent linkedIdent = null;

    @Nonnull
    @Override
    public CodeIdentBuilder setIdent(String ident) {
        this.ident = ident;
        return this;
    }

    @Nonnull
    @Override
    public CodeIdentBuilder setFirstIdent(String firstIdent) {
        this.firstIdent = firstIdent;
        return this;
    }

    @Nonnull
    @Override
    public CodeIdentBuilder linkedIdent(CodeIdent previousIdent) {
        this.linkedIdent = previousIdent;
        return this;
    }

    @Nonnull
    @Override
    public CodeIdent build() {
        CodeIdent result;
        if (firstIdent == null) {
            if (ident == null) {
                result = CodeIdentVoid.getInstance();
            } else {
                result = CodeIdentSimple.of(ident);
            }
        } else {
            result = CodeIdentFirst.of(firstIdent, (ident == null) ? "" : ident);
        }
        if (linkedIdent != null) {
            result = CodeIdentLinked.of(result, linkedIdent);
        }
        return result;
    }
}
