package com.provys.provysdb.dbcontext.jakarta;

import com.provys.provysdb.dbcontext.impl.ProvysDbConfiguration;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Class retrieves parameters of database connection from environment. Used by {@code
 * ProvysConnectionPoolDataSource} as source of properties for establishing connection
 */
@ApplicationScoped
class ProvysDbConfigurationJakarta implements ProvysDbConfiguration {

  private final String url;

  private final String user;

  private final String pwd;

  private final int minPoolSize;

  private final int maxPoolSize;

  @Inject
  ProvysDbConfigurationJakarta(@ConfigProperty(name = "provysdb.url") String url,
      @ConfigProperty(name = "provysdb.user") String user,
      @ConfigProperty(name = "provysdb.pwd") String pwd,
      @ConfigProperty(name = "provysdb.minpoolsize", defaultValue = "1") int minPoolSize,
      @ConfigProperty(name = "provysdb.maxpoolsize", defaultValue = "10") int maxPoolSize) {
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
      throw new IllegalArgumentException(
          "Invalid value of property provysdb.minpoolsize " + minPoolSize);
    }
    this.minPoolSize = minPoolSize;
    if (maxPoolSize <= 0) {
      throw new IllegalArgumentException(
          "Invalid value of property provysdb.maxpoolsize " + maxPoolSize);
    }
    this.maxPoolSize = maxPoolSize;
  }

  @Override
  public String getUrl() {
    return url;
  }

  @Override
  public String getUser() {
    return user;
  }

  @Override
  public String getPwd() {
    return pwd;
  }

  @Override
  public int getMinPoolSize() {
    return minPoolSize;
  }

  @Override
  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  @Override
  public String toString() {
    return "ProvysDbConfigurationJakarta{"
        + "url='" + url + '\''
        + ", user='" + user + '\''
        + ", minPoolSize=" + minPoolSize
        + ", maxPoolSize=" + maxPoolSize
        + '}';
  }
}
