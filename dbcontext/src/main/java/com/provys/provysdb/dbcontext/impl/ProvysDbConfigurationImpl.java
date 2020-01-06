package com.provys.provysdb.dbcontext.impl;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

/**
 * Class retrieves parameters of database connection from environment. Used by {@code ProvysConnectionPoolDataSource}
 * as source of properties for establishing connection
 */
@ApplicationScoped
@ConfigurationProperties(prefix = "provysdb")
class ProvysDbConfigurationImpl implements ProvysDbConfiguration {

    @Nonnull
    private final String url;

    @Nonnull
    private final String user;

    @Nonnull
    private final String pwd;

    private final int minPoolSize;

    private final int maxPoolSize;

    @Inject
    @ConstructorBinding
    ProvysDbConfigurationImpl(@ConfigProperty(name = "provysdb.url") String url,
                              @ConfigProperty(name = "provysdb.user") String user,
                              @ConfigProperty(name = "provysdb.pwd") String pwd,
                              @ConfigProperty(name = "provysdb.minpoolsize", defaultValue = "1")
                              @DefaultValue("1") int minPoolSize,
                              @ConfigProperty(name = "provysdb.maxpoolsize", defaultValue = "10")
                              @DefaultValue("10") int maxPoolSize) {
        if (Objects.requireNonNull(url, "Property provysdb.url not specified").isBlank()) {
            throw new IllegalArgumentException("Property provysdb.url cannot be blank");
        }
        this.url = url;
        if (Objects.requireNonNull(user, "Property provysdb.user not specified").isBlank()) {
            throw new IllegalArgumentException("Property provysdb.user cannot be blank");
        }
        this.user = user;
        if (Objects.requireNonNull(pwd, "Property provysdb.pwd not specified").isBlank()) {
            throw new IllegalArgumentException("Property provysdb.pwd cannot be blank");
        }
        this.pwd = pwd;
        if (minPoolSize <= 0) {
            throw new IllegalArgumentException("Invalid value of property provysdb.minpoolsize " + minPoolSize);
        }
        this.minPoolSize = minPoolSize;
        if (maxPoolSize <= 0) {
            throw new IllegalArgumentException("Invalid value of property provysdb.maxpoolsize " + maxPoolSize);
        }
        this.maxPoolSize = maxPoolSize;
    }

    @Override
    @Nonnull
    public String getUrl() {
        return url;
    }

    @Override
    @Nonnull
    public String getUser() {
        return user;
    }

    @Override
    @Nonnull
    public String getPwd() {
        return pwd;
    }

    @Override
    public int getMinPoolSize() {
        return minPoolSize;
    }

    /**
     * @return value of field maxPoolSize
     */
    @Override
    public int getMaxPoolSize() {
        return maxPoolSize;
    }
}
