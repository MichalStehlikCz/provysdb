package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Interface represents element of from clause - e.g. either just table to be selected from or join clause
 */
public interface SqlFrom extends SqlElement {

    /**
     * @return alias this table is associated with; mandatory for element in from clause, as even though it is not
     * required in SQL, it is enforced by PROVYS StyleGuide
     */
    @Nonnull
    SqlTableAlias getAlias();
}
