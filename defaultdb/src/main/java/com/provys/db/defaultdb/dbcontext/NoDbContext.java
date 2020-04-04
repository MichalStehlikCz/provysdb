package com.provys.db.defaultdb.dbcontext;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeHandler;
import com.provys.db.defaultdb.types.SqlTypeMap;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default context can be used to construct statements, compatible with Provys mapping of data
 * types, but without database connection.
 */
public class NoDbContext implements DbContext, Serializable {

  private static final DbContext INSTANCE = new NoDbContext();

  public static DbContext getInstance() {
    return INSTANCE;
  }

  private final SqlTypeHandler sqlTypeHandler;

  /**
   * Creates default database context with supplied type map.
   *
   * @param sqlTypeHandler is type map to be used for this database context
   */
  public NoDbContext(SqlTypeHandler sqlTypeHandler) {
    this.sqlTypeHandler = sqlTypeHandler;
  }

  /**
   * Creates default database context with default type map.
   */
  public NoDbContext() {
    this(SqlTypeMap.getDefaultMap());
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
  public SqlTypeHandler getSqlTypeHandler() {
    return sqlTypeHandler;
  }

  /**
   * Supports serialization via SerializationProxy.
   *
   * @return proxy, corresponding to this SimpleName
   */
  protected Object writeReplace() {
    return new SerializationProxy(this);
  }

  /**
   * Should be serialized via proxy, thus no direct deserialization should occur.
   *
   * @param stream is stream from which object is to be read
   * @throws InvalidObjectException always
   */
  private void readObject(ObjectInputStream stream) throws InvalidObjectException {
    throw new InvalidObjectException("Use Serialization Proxy instead.");
  }

  private static final class SerializationProxy implements Serializable {

    private static final long serialVersionUID = 7701122454882533569L;
    private @Nullable SqlTypeHandler sqlTypeHandler;

    SerializationProxy() {
    }

    SerializationProxy(NoDbContext value) {
      if (value.sqlTypeHandler.equals(SqlTypeMap.getDefaultMap())) {
        this.sqlTypeHandler = null;
      } else {
        this.sqlTypeHandler = value.sqlTypeHandler;
      }
    }

    private Object readResolve() {
      if (sqlTypeHandler == null) {
        return getInstance();
      }
      return new NoDbContext(sqlTypeHandler);
    }
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
    return sqlTypeHandler.equals(that.sqlTypeHandler);
  }

  @Override
  public int hashCode() {
    return sqlTypeHandler.hashCode();
  }

  @Override
  public String toString() {
    return "DefaultDbContext{"
        + "sqlTypeMap=" + sqlTypeHandler
        + '}';
  }
}
