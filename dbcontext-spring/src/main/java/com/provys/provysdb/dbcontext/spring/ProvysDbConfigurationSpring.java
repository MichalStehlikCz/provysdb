package com.provys.provysdb.dbcontext.spring;

import com.provys.provysdb.dbcontext.impl.ProvysDbConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Class retrieves parameters of database connection from environment. Used by {@code ProvysConnectionPoolDataSource}
 * as source of properties for establishing connection
 */
@ConfigurationProperties(prefix = "provysdb")
public class ProvysDbConfigurationSpring implements ProvysDbConfiguration {

    @Nonnull
    private final String url;

    @Nonnull
    private final String user;

    @Nonnull
    private final String pwd;

    private final int minPoolSize;

    private final int maxPoolSize;

    @ConstructorBinding
    ProvysDbConfigurationSpring(String url, String user, String pwd, @DefaultValue("1") int minPoolSize,
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
