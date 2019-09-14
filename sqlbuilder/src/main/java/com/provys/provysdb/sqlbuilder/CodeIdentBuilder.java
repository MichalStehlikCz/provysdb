package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

public interface CodeIdentBuilder {
    /**
     * Set ident (used either on all lines or starting from second line, depending if firstIdent is set)
     *
     * @param ident is ident to be used in new ident
     * @return self to support fluent build
     */
    @Nonnull
    CodeIdentBuilder setIdent(String ident);

    /**
     * Set specific ident for the first line
     *
     * @param firstIdent is ident to be used for the first line in new ident
     * @return self to support fluent build
     */
    @Nonnull
    CodeIdentBuilder setFirstIdent(String firstIdent);

    /**
     * Link new ident to specified (previous) ident. Linking means that new ident will be appended to previous ident
     * and supplied ident will be used on each line together with new ident
     *
     * @param previousIdent is ident that will be used as prefix
     * @return self to support fluent build
     */
    @Nonnull
    CodeIdentBuilder linkedIdent(CodeIdent previousIdent);

    /**
     * @return ident built in this builder
     */
    @Nonnull
    CodeIdent build();
}
