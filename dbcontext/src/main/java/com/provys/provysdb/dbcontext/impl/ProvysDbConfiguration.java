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
        return Objects.requireNonNull(url, "Property provysdb.url not specified");
    }

    /**
     * @return value of field user
     */
    @Nonnull
    public String getUser() {
        return Objects.requireNonNull(user, "Property provysdb.user not specified");
    }

    /**
     * @return value of field pwd
     */
    public String getPwd() {
        return Objects.requireNonNull(pwd, "Property provysdb.pwd not specified");
    }

    /**
     * @return value of field minPoolSize
     */
    public int getMinPoolSize() {
        if (minPoolSize <= 0) {
            throw new IllegalArgumentException("Invalid value of property provysdb.minpoolsize " + minPoolSize);
        }
        return minPoolSize;
    }

    /**
     * @return value of field maxPoolSize
     */
    public int getMaxPoolSize() {
        if (maxPoolSize <= 0) {
            throw new IllegalArgumentException("Invalid value of property provysdb.maxpoolsize " + maxPoolSize);
        }
        return maxPoolSize;
    }
}
