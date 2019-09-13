package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

public interface SqlFrom extends SqlElement {

    /**
     * @return alias this column is associated with; empty optional if column has no alias. Note that if it is simple
     * column, its name is also used as alias
     */
    @Nonnull
    String getAlias();
}
