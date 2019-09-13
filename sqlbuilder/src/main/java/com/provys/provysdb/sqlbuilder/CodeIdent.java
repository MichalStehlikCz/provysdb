package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Represents ident that is to be used for text
 */
public interface CodeIdent {
    /**
     * @return current ident text
     */
    @Nonnull
    String get();

    /**
     * Append current ident to supplied builder and mark it as used (e.g. deactivate first ident)
     */
    void use(StringBuilder builder);
}
