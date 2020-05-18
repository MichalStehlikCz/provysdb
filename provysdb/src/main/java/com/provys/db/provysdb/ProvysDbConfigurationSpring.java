package com.provys.db.provysdb;

import com.google.errorprone.annotations.Immutable;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Class retrieves parameters of database connection from environment. Used by {@code
 * ProvysConnectionPoolDataSource} as source of properties for establishing connection
 */
@ConfigurationProperties(prefix = "provysdb")
@Immutable
public class ProvysDbConfigurationSpring implements ProvysDbConfiguration {

  private static final long serialVersionUID = -269447777183213755L;

  private final String url;

  private final String user;

  private final String pwd;

  private final int minPoolSize;

  private final int maxPoolSize;

  private final int connectionReuseThreshold;

  private final boolean validateOnBorrow;

  private final int validateSkipUntil;

  @ConstructorBinding
  ProvysDbConfigurationSpring(String url, String user, String pwd,
      @DefaultValue("1") int minPoolSize, @DefaultValue("10") int maxPoolSize,
      @DefaultValue("-1") int connectionReuseThreshold,
      @DefaultValue("true") boolean validateOnBorrow,
      @DefaultValue("0") int validateSkipUntil) {
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
    this.connectionReuseThreshold =
        connectionReuseThreshold >= 0 ? connectionReuseThreshold : maxPoolSize;
    this.validateOnBorrow = validateOnBorrow;
    if (validateSkipUntil < 0) {
      throw new IllegalArgumentException(
          "Invalid value of property provysdb.validateskipuntil " + validateSkipUntil);
    }
    this.validateSkipUntil = validateSkipUntil;
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
  public int getConnectionReuseThreshold() {
    return connectionReuseThreshold;
  }

  @Override
  public boolean isValidateOnBorrow() {
    return validateOnBorrow;
  }

  @Override
  public int getValidateSkipUntil() {
    return validateSkipUntil;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProvysDbConfigurationSpring that = (ProvysDbConfigurationSpring) o;
    return minPoolSize == that.minPoolSize
        && maxPoolSize == that.maxPoolSize
        && connectionReuseThreshold == that.connectionReuseThreshold
        && validateOnBorrow == that.validateOnBorrow
        && validateSkipUntil == that.validateSkipUntil
        && url.equals(that.url)
        && user.equals(that.user)
        && pwd.equals(that.pwd);
  }

  @Override
  public int hashCode() {
    int result = url.hashCode();
    result = 31 * result + user.hashCode();
    result = 31 * result + pwd.hashCode();
    result = 31 * result + minPoolSize;
    result = 31 * result + maxPoolSize;
    result = 31 * result + connectionReuseThreshold;
    result = 31 * result + (validateOnBorrow ? 1 : 0);
    result = 31 * result + validateSkipUntil;
    return result;
  }

  @Override
  public String toString() {
    return "ProvysDbConfigurationSpring{"
        + "url='" + url + '\''
        + ", user='" + user + '\''
        + ", pwd='" + pwd + '\''
        + ", minPoolSize=" + minPoolSize
        + ", maxPoolSize=" + maxPoolSize
        + ", connectionReuseThreshold=" + connectionReuseThreshold
        + ", validateOnBorrow=" + validateOnBorrow
        + ", validateSkipUntil=" + validateSkipUntil
        + '}';
  }
}
