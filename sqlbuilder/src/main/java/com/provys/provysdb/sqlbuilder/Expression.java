package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Represents SQL expression; untyped interface, used in places where type is not needed
 */
public interface Expression {

    /**
     * Adds sql text to string builder, used to construct statement
     *
     * @param builder is StringBuilder, used to construct sql text
     */
    void addSql(CodeBuilder builder);

    /**
     * @return list of binds, used in this element
     */
    @Nonnull
    Collection<BindVariable> getBinds();
}
