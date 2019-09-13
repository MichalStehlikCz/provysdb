package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Class represents sql statement, described in structured way. This statement can be used to initiate
 * ProvysPreparedStatement object for execution
 */
public interface Select {
    /**
     * @return sql select text built from this select statement
     */
    @Nonnull
    String getSql();

    /**
     * @return collection of bind variables used in this statement
     */
    @Nonnull
    Collection<BindVariable> getBindVariables();
}
