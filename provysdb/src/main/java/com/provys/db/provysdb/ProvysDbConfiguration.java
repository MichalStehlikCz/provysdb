package com.provys.db.provysdb;

import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;

/**
 * Interface represents configuration needed for datasource initiation. Used internally when
 * initiating connection pool data source
 */
@Immutable
public interface ProvysDbConfiguration extends Serializable {

  /**
   * Url defining connection to Provys database.
   *
   * @return value of field url
   */
  String getUrl();

  /**
   * Database user used for connection.
   *
   * @return value of field user
   */
  String getUser();

  /**
   * Password used for connection.
   *
   * @return value of field pwd
   */
  String getPwd();

  /**
   * Minimal number of connections in pool.
   *
   * @return value of field minPoolSize
   */
  int getMinPoolSize();

  /**
   * Maximal number of connections in pool.
   *
   * @return value of field maxPoolSize
   */
  int getMaxPoolSize();

  /**
   * Defines how many connections have to be in pool in order to reuse connection with "high cost".
   * High cost reuse is when login is required in order to switch PROVYS user context in database
   * session. Defaults to pool size.
   *
   * @return minimal number of instances in pool when switch to different user is attempted instead
   *     of creation of new connection
   */
  int getConnectionReuseThreshold();

  /**
   * Defines if pool should validate connection on borrow. Validation is just simple roundtrip, it
   * does not verify that existing state was discarded exception will not be thrown
   *
   * @return is pool should validate connection on borrow
   */
  boolean isValidateOnBorrow();

  /**
   * Defines that pool can skip validation of idle connections if connection has been active less
   * than given number of seconds ago (SecondsToTrustIdleConnection pool parameter).
   *
   * @return how long after last use can be connection validation skipped
   */
  int getValidateSkipUntil();
}
