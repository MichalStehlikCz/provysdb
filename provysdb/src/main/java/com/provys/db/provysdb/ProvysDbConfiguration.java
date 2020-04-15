package com.provys.db.provysdb;

import com.google.errorprone.annotations.Immutable;

/**
 * Interface represents configuration needed for datasource initiation. Used internally when
 * initiating connection pool data source
 */
@Immutable
public interface ProvysDbConfiguration {

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
}
