package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class represents sql statement, described in structured way. This statement can be used to initiate
 * ProvysPreparedStatement object for execution
 */
public interface Select {
    /**
     * @return sql select text built from this select statement
     */
    @Nonnull
    String getSqlText();

    /**
     * @return collection of bind variables used in this statement
     */
    @Nonnull
    Collection<BindVariable> getBinds();

    /**
     * @return number of bind positions (represented by ?) in statement
     */
    int getPositions();

    /**
     * @return list of bind variables along with their associated positions
     */
    Map<BindVariable, List<Integer>> getBindPositions();
}
