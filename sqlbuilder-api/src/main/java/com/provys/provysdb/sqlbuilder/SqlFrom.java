package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

public interface SqlFrom extends SqlElement {

    /**
     * @return alias this table is associated with; mandatory for element in from clause, as even though it is not
     * required in SQL, it is enforced by PROVYS StyleGuide
     */
    @Nonnull
    SqlTableAlias getAlias();
}
