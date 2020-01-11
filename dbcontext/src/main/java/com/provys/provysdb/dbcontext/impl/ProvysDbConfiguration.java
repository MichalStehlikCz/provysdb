package com.provys.provysdb.dbcontext.impl;

import javax.annotation.Nonnull;

/**
 * Interface represents configuration needed for datasource initiation. Used internally when initiating connection pool
 * data source
 */
public interface ProvysDbConfiguration {
    /**
     * @return value of field url
     */
    @Nonnull
    String getUrl();

    /**
     * @return value of field user
     */
    @Nonnull
    String getUser();

    /**
     * @return value of field pwd
     */
    @Nonnull
    String getPwd();

    /**
     * @return value of field minPoolSize
     */
    int getMinPoolSize();

    /**
     * @return value of field maxPoolSize
     */
    int getMaxPoolSize();
}
