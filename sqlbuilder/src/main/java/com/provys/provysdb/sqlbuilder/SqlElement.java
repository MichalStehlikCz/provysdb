package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Collection;

interface SqlElement {

    /**
     * Adds sql text to string builder, used to construct statement
     *
     * @param builder is StringBuilder, used to construct sql text
     */
    void addSql(SelectBuilder selectBuilder, CodeBuilder builder);

    /**
     * @return list of binds, used in this element
     */
    @Nonnull
    Collection<BindVariable> getBinds();
}
