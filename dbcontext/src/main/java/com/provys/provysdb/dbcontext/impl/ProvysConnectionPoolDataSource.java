package com.provys.provysdb.dbcontext.impl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface describing {@code DataSource} to be used for accessing PROVYS database. Adds option to
 * retrieve connection using token
 *
 * @author stehlik
 */
interface ProvysConnectionPoolDataSource extends DataSource {

  Connection getConnectionWithToken(String dbToken) throws SQLException;

  String getUser();

  String getUrl();
}
