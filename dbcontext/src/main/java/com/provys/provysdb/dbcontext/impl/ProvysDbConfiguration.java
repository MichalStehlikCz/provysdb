package com.provys.provysdb.dbcontext.impl;

/**
 * Interface represents configuration needed for datasource initiation. Used internally when initiating connection pool
 * data source
 */
public interface ProvysDbConfiguration {
    /**
     * @return value of field url
     */
    String getUrl();

    /**
     * @return value of field user
     */
    String getUser();

    /**
     * @return value of field pwd
     */
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
