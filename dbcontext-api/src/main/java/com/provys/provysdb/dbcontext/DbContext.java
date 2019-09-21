package com.provys.provysdb.dbcontext;

import javax.annotation.Nonnull;

/**
 * Represents database context for Sql builder - statements are deferred to this context's connections
 */
public interface DbContext {

    @Nonnull
    public DbConnection getConnection();

    /**
     * Retrieve connection that can be used to access Provys database. Uses ProvysConnection wrapper that provides
     * monitoring, logging and wrappers around prepared statement and result-set, supporting Provys framework specific
     * classes / mapping to types used in database. Uses token to switch to particular Provys user account
     *
     * @param dbToken is valid token, registered in Provys database
     * @return retrieved connection
     */
    @Nonnull
    public DbConnection getConnection(String dbToken);

    /**
     * @return username used to open connection to Provys database
     */
    @Nonnull
    String getUser();

    /**
     * @return URL of database provys data-source is connected to
     */
    @Nonnull
    String getUrl();
}
