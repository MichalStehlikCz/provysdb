package com.provys.db.provysdb;

import com.provys.auth.api.UserContext;
import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.SqlTypeHandler;
import com.provys.db.defaultdb.types.SqlTypeMap;
import java.sql.Connection;
import java.sql.SQLException;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Implements database context with Oracle connection, adjusted to current user context.
 */
public final class UserDbContext extends ProvysDbContext {

  private final UserContext userContext;

  /**
   * Create user database context based on supplied provys data source, type map and user context
   * provider.
   *
   * @param provysDataSource is data source that can supply connections to Provys database based on
   *                         provided user data
   * @param sqlTypeHandler   is type handler to be used for new connection
   * @param userContext      is provider of user context for logged in user
   */
  public UserDbContext(ProvysConnectionPoolDataSource provysDataSource,
      SqlTypeHandler sqlTypeHandler, UserContext userContext) {
    super(provysDataSource, sqlTypeHandler);
    this.userContext = userContext;
  }

  /**
   * Create user database context based on supplied provys data source and user context provider.
   * Uses default type map.
   *
   * @param provysDataSource is data source that can supply connections to Provys database based on
   *                         provided user data
   * @param userContext      is provider of user context for logged in user
   */
  public UserDbContext(ProvysConnectionPoolDataSource provysDataSource, UserContext userContext) {
    this(provysDataSource, SqlTypeMap.getDefaultMap(), userContext);
  }

  @Override
  protected Connection getConnectionInt() throws SQLException {
    return getProvysDataSource().getConnectionForUser(userContext.getCurrentUser());
  }

  @Override
  public DtUid getProvysUserId() {
    return userContext.getCurrentUserId();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDbContext that = (UserDbContext) o;
    return userContext.equals(that.userContext);
  }

  @Override
  public int hashCode() {
    return userContext.hashCode();
  }

  @Override
  public String toString() {
    return "UserDbContext{"
        + "userContext=" + userContext
        + ", " + super.toString() + '}';
  }
}
