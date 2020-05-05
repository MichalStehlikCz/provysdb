package com.provys.db.sqlquery.query;

import com.provys.db.dbcontext.DbContext;
import com.provys.db.query.elements.SelectT;
import com.provys.db.query.elements.SelectT1;
import com.provys.db.query.elements.SelectT2;
import com.provys.db.sqlquery.literals.SqlLiteralHandler;
import com.provys.db.sqlquery.literals.SqlLiteralTypeHandlerMap;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default statement builder. Uses supplied handlers for individual types of elements.
 */
public final class DefaultStatementFactory implements StatementFactory {

  private final DbContext dbContext;
  private final SqlLiteralHandler sqlLiteralHandler;
  private final SqlBuiltInMap sqlBuiltInMap;

  /**
   * Create statement factory based on supplied database context, literal handler and function map.
   *
   * @param dbContext         is database context used as source for connections
   * @param sqlLiteralHandler is literal handler, used to produce Sql literal values
   * @param sqlBuiltInMap    is function map, defining templates for sql built-in functions
   */
  public DefaultStatementFactory(DbContext dbContext, SqlLiteralHandler sqlLiteralHandler,
      SqlBuiltInMap sqlBuiltInMap) {
    this.dbContext = dbContext;
    this.sqlLiteralHandler = sqlLiteralHandler;
    this.sqlBuiltInMap = sqlBuiltInMap;
  }

  /**
   * Create statement factory based on supplied database context; use default literal handler and
   * function map. Such setting should be compatible with Oracle database.
   *
   * @param dbContext is database context used as source for connections
   */
  public DefaultStatementFactory(DbContext dbContext) {
    this(dbContext, SqlLiteralTypeHandlerMap.getDefaultMap(), SqlBuiltInMapImpl.getDefault());
  }

  /**
   * Value of field dbContext.
   *
   * @return value of field dbContext
   */
  public DbContext getDbContext() {
    return dbContext;
  }

  /**
   * Value of field sqlLiteralHandler.
   *
   * @return value of field sqlLiteralHandler
   */
  public SqlLiteralHandler getSqlLiteralHandler() {
    return sqlLiteralHandler;
  }

  /**
   * Value of field sqlFunctionMap.
   *
   * @return value of field sqlFunctionMap
   */
  public SqlBuiltInMap getSqlBuiltInMap() {
    return sqlBuiltInMap;
  }

  private DefaultSqlBuilder getSqlBuilder() {
    return new DefaultSqlBuilder(sqlLiteralHandler, sqlBuiltInMap);
  }

  @Override
  public SelectStatement getSelect(SelectT<?> query) {
    var builder = getSqlBuilder();
    query.apply(builder);
    return new SelectStatementImpl(builder.getSql(), builder.getBindsWithPos(),
        builder.getBindValues(), dbContext);
  }

  @Override
  public <T1> SelectStatementT1<T1> getSelect(SelectT1<T1> query) {
    var builder = getSqlBuilder();
    query.apply(builder);
    return new SelectStatementT1Impl<>(builder.getSql(), builder.getBindsWithPos(),
        builder.getBindValues(), dbContext, query.getType1());
  }

  @Override
  public <T1, T2> SelectStatementT2<T1, T2> getSelect(SelectT2<? extends T1, ? extends T2> query) {
    var builder = getSqlBuilder();
    query.apply(builder);
    return new SelectStatementT2Impl<>(builder.getSql(), builder.getBindsWithPos(),
        builder.getBindValues(), dbContext, query.getType1(), query.getType2());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultStatementFactory that = (DefaultStatementFactory) o;
    return dbContext.equals(that.dbContext)
        && sqlLiteralHandler.equals(that.sqlLiteralHandler)
        && sqlBuiltInMap.equals(that.sqlBuiltInMap);
  }

  @Override
  public int hashCode() {
    int result = dbContext.hashCode();
    result = 31 * result + sqlLiteralHandler.hashCode();
    result = 31 * result + sqlBuiltInMap.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultStatementFactory{"
        + "dbContext=" + dbContext
        + ", sqlLiteralHandler=" + sqlLiteralHandler
        + ", sqlFunctionMap=" + sqlBuiltInMap
        + '}';
  }
}
