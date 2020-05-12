package com.provys.db.provysdb;

import com.provys.common.datatype.DtUid;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Interface describing {@code DataSource} to be used for accessing PROVYS database. Adds option to
 * retrieve connection using token
 *
 * @author stehlik
 */
public interface ProvysConnectionPoolDataSource extends DataSource {

  /**
   * Retrieve database connection using token.
   *
   * @param dbToken is token supplied to database to switch to Provys user context
   * @return connection with proper Provys user context
   * @throws SQLException if there is any problem retrieving connection
   */
  Connection getConnectionWithToken(String dbToken) throws SQLException;

  /**
   * Url of Provys database.
   *
   * @return url of Provys database
   */
  String getUrl();

  /**
   * Oracle user, used to connect to Provys database.
   *
   * @return Oracle user, used to connect to Provys database
   */
  String getUser();

  /**
   * Provys user account Id, corresponding to account used to connect to database.
   *
   * @return provys user Id for account, used to connect to database
   */
  DtUid getProvysUserId();
}
