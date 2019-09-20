package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Class supplies connections to underlying Provys database and gives access to Sql implementation
 */
public interface SqlFactory {
    /**
     * @return sql context associated with provys connection, using default (internal) user account
     */
    @Nonnull
    Sql getSql();

    /**
     * @param dbToken is token to be used to switch to specific provys user account
     * @return sql context associated with provys connection, using supplied token
     */
    @Nonnull
    Sql getSql(String dbToken);
}
