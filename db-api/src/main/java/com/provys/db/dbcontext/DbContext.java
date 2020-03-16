package com.provys.db.dbcontext;

/**
 * Represents database context for Sql builder - statements are deferred to this context's
 * connections.
 */
public interface DbContext {

  /**
   * Retrieve connection without user context to Provys database. This connection is generally used
   * for actions that do not require user context - e.g. loading configuration or performing
   * asynchronous actions, that are made under system and not user credentials
   *
   * @return admin (no user contexted) connection
   */
  DbConnection getConnection();

  /**
   * Retrieve connection that can be used to access Provys database. Uses ProvysConnection wrapper
   * that provides monitoring, logging and wrappers around prepared statement and result-set,
   * supporting Provys framework specific classes / mapping to types used in database. Uses token to
   * switch to particular Provys user account
   *
   * @param dbToken is valid token, registered in Provys database
   * @return retrieved connection
   */
  DbConnection getConnection(String dbToken);

  /**
   * Oracle user used to access Provys database.
   *
   * @return username used to open connection to Provys database
   */
  String getUser();

  /**
   * URL of database connections are connected to. It is Oracle JDBC thin URL, might include
   * fail-over
   *
   * @return URL of database provys data-source is connected to
   */
  String getUrl();

  /**
   * Sql type map to be used with connections, retrieved from this database context.
   *
   * @return sql type adapter map used in given database context
   */
  SqlTypeMap getSqlTypeMap();
}
