package com.provys.provysdb.dbcontext.impl;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

/**
 * Class retrieves parameters of database connection from environment. Used by {@code ProvysConnectionPoolDataSource}
 * as source of properties for establishing connection
 */
@ApplicationScoped
class ProvysDbConfiguration {

    @Inject
    @ConfigProperty(name = "provysdb.url")
    private String url;

    @Inject
    @ConfigProperty(name = "provysdb.user")
    private String user;

    @Inject
    @ConfigProperty(name = "provysdb.pwd")
    private String pwd;

    @Inject
    @ConfigProperty(name = "provysdb.minpoolsize", defaultValue = "1")
    private int minPoolSize;

    @Inject
    @ConfigProperty(name = "provysdb.maxpoolsize", defaultValue = "10")
    private int maxPoolSize;

    /**
     * @return value of field url
     */
    @Nonnull
    public String getUrl() {
        return Objects.requireNonNull(url);
    }

    /**
     * @return value of field user
     */
    @Nonnull
    public String getUser() {
        return Objects.requireNonNull(user);
    }

    /**
     * @return value of field pwd
     */
    public String getPwd() {
        return Objects.requireNonNull(pwd);
    }

    /**
     * @return value of field minPoolSize
     */
    public int getMinPoolSize() {
        return minPoolSize;
    }

    /**
     * @return value of field maxPoolSize
     */
    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
