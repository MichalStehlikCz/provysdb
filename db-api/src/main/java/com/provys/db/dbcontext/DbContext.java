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
   * @return connection to provys database
   */
  DbConnection getConnection();

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
   * @return com.provys.db.sql type adapter map used in given database context
   */
  SqlTypeHandler getSqlTypeHandler();
}
