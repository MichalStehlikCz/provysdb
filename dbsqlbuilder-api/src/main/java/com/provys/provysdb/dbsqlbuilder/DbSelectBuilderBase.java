package com.provys.provysdb.dbsqlbuilder;

import javax.annotation.Nonnull;

interface DbSelectBuilderBase {

    /**
     * Build select statement from builder
     *
     * @return select statement based on builder's content
     */
    @Nonnull
    SelectStatement prepare();
}
