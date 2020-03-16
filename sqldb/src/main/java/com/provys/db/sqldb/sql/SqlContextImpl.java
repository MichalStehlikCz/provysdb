package com.provys.db.sqldb.sql;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.Condition;
import com.provys.db.sql.FromClause;
import com.provys.db.sql.Function;
import com.provys.db.sql.SelectClause;
import com.provys.db.sqldb.dbcontext.NoDbContext;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Implements factory methods for generic Sql object creation. Wraps {@link DbContext} that provides database connectivity.
 */
public final class SqlContextImpl implements SqlContext<SqlSelect, SqlSelectClause, SqlSelectColumn,
    SqlFromClause, SqlFromElement, SqlCondition, SqlExpression> {

  private static final SqlContextImpl NO_DB_INSTANCE = new SqlContextImpl(NoDbContext.getInstance(),
      SqlFunctionMapImpl.getDefault());

  /**
   * Instance of generic sql factory without database connectivity.
   *
   * @return instance of generic sql factory without database connectivity
   */
  public static SqlContextImpl getNoDbInstance() {
    return NO_DB_INSTANCE;
  }

  /**
   * Create sql context wrapping specified database context.
   *
   * @param dbContext is database context that will provide connections
   * @param functionMap is mapping of sql functions to templates
   */
  public static SqlContextImpl forDbContext(DbContext dbContext, SqlFunctionMap functionMap) {
    if (dbContext.equals(NoDbContext.getInstance())
        && functionMap.equals(SqlFunctionMapImpl.getDefault())) {
      return NO_DB_INSTANCE;
    }
    return new SqlContextImpl(dbContext, functionMap);
  }

  private final DbContext dbContext;
  private final SqlFunctionMap functionMap;

  private SqlContextImpl(DbContext dbContext, SqlFunctionMap functionMap) {
    this.dbContext = dbContext;
    this.functionMap = functionMap;
  }

  @Override
  public SqlExpression literal(Object value) {
    return new SqlLiteral(this, value);
  }

  @Override
  public SqlExpression literalNVarchar(String value) {
    return new SqlLiteralNVarchar(this, value);
  }

  /**
   * Create select statement with specified select clause and from clause.
   *
   * @param selectClause defines projection of from clause to results
   * @param fromClause   defines data sources
   * @param bindMap      if specified, replace bind variables with ones found in this map
   * @return select based on supplied clauses
   */
  @Override
  public SqlSelect select(SelectClause selectClause, FromClause fromClause,
      @Nullable BindMap bindMap) {
    return null;
  }

  /**
   * Create select statement with specified select clause, from clause and where clause.
   *
   * @param selectClause defines projection of from clause to results
   * @param fromClause   defines data sources
   * @param whereClause  defines filtering criteria
   * @param bindMap      if specified, replace bind variables with ones found in this map
   * @return select based on supplied clauses
   */
  @Override
  public SqlSelect select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable BindMap bindMap) {
    return null;
  }

  @Override
  public String getFunctionTemplate(Function function) {
    return functionMap.getTemplate(function);
  }

  @Override
  public DbConnection getConnection() {
    return dbContext.getConnection();
  }

  @Override
  public DbConnection getConnection(String dbToken) {
    return dbContext.getConnection(dbToken);
  }

  @Override
  public String getUser() {
    return dbContext.getUser();
  }

  @Override
  public String getUrl() {
    return dbContext.getUrl();
  }

  @Override
  public SqlTypeMap getSqlTypeMap() {
    return dbContext.getSqlTypeMap();
  }

  @Override
  public String toString() {
    return "SqlContextImpl{"
        + "dbContext=" + dbContext
        + '}';
  }
}
