package com.provys.db.sqldb.sql;

import com.provys.db.dbcontext.DbConnection;
import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.Condition;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromClause;
import com.provys.db.sql.Function;
import com.provys.db.sql.SelectClause;
import com.provys.db.sqldb.dbcontext.NoDbContext;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Implements factory methods for generic Sql object creation. Wraps {@link DbContext} that provides
 * database connectivity.
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
   * @param dbContext   is database context that will provide connections
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
   * Call to function specified by enum. Supplied expressions must correspond to supported arguments
   * of function. Resulting expression has value of type, corresponding to function result type
   *
   * @param function is function to be invoked
   * @param argument is list of arguments to be passed to function
   * @return expression that evaluates to call to CHR function applied on code
   */
  @Override
  public SqlExpression function(Function function, Expression... argument) {
    return null;
  }

  /**
   * Call to function specified by enum. Supplied expressions must correspond to supported arguments
   * of function. Resulting expression has value of type, corresponding to function result type
   *
   * @param function is function to be invoked
   * @param argument is list of arguments to be passed to function
   * @return expression that evaluates to call to CHR function applied on code
   */
  @Override
  public SqlExpression function(Function function, List<Expression> argument) {
    return null;
  }

  @Override
  public SqlSelect select(SelectClause selectClause, FromClause fromClause,
      @Nullable BindMap bindMap) {
    return new SqlSelectImpl(this, selectClause, fromClause, null,  bindMap);
  }

  @Override
  public SqlSelect select(SelectClause selectClause, FromClause fromClause,
      @Nullable Condition whereClause, @Nullable BindMap bindMap) {
    return new SqlSelectImpl(this, selectClause, fromClause, whereClause, bindMap);
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
