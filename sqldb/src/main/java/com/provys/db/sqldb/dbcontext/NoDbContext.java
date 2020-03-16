package com.provys.db.sqldb.dbcontext;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeMap;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default context can be used to construct statements, compatible with Provys mapping of data
 * types, but without database connection.
 */
public class NoDbContext implements DbContext {

  private static final DbContext INSTANCE = new NoDbContext();

  public static DbContext getInstance() {
    return INSTANCE;
  }

  private final SqlTypeMap sqlTypeMap;

  /**
   * Creates default database context with supplied type map.
   *
   * @param sqlTypeMap is type map to be used for this database context
   */
  public NoDbContext(SqlTypeMap sqlTypeMap) {
    this.sqlTypeMap = sqlTypeMap;
  }

  /**
   * Creates default database context with default type map.
   */
  public NoDbContext() {
    this(DefaultTypeMapImpl.getDefaultMap());
  }

  private static InternalException getNotConnected() {
    return new InternalException("Default context is not connected to database");
  }

  @Override
  public DbConnection getConnection() {
    throw getNotConnected();
  }

  @Override
  public DbConnection getConnection(String dbToken) {
    throw getNotConnected();
  }

  @Override
  public String getUser() {
    throw getNotConnected();
  }

  @Override
  public String getUrl() {
    throw getNotConnected();
  }

  @Override
  public SqlTypeMap getSqlTypeMap() {
    return sqlTypeMap;
  }

  @Override
  @SuppressWarnings("EqualsGetClass")
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NoDbContext that = (NoDbContext) o;
    return Objects.equals(sqlTypeMap, that.sqlTypeMap);
  }

  @Override
  public int hashCode() {
    return sqlTypeMap != null ? sqlTypeMap.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "DefaultDbContext{"
        + "sqlTypeMap=" + sqlTypeMap
        + '}';
  }
}
