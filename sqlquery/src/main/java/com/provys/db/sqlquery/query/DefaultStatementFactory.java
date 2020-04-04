package com.provys.db.sqlquery.query;

import com.provys.db.dbcontext.DbContext;
import com.provys.db.query.elements.Select;
import com.provys.db.sqlquery.dbcontext.NoDbContext;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default statement builder. Uses supplied handlers for individual types of elements.
 */
public class DefaultStatementFactory implements StatementFactory {

  private static final DefaultStatementFactory NO_DB_FACTORY = new DefaultStatementFactory(
      NoDbContext.getInstance(), SqlFunctionMapImpl.getDefault());

  public static DefaultStatementFactory getNoDbFactory() {
    return NO_DB_FACTORY;
  }

  private final DbContext dbContext;
  private final SqlFunctionMap sqlFunctionMap;
  private final Map<Class<?>, ElementTypeSqlBuilder<StatementFactory, ?>> elementBuilders;

  public DefaultStatementFactory(DbContext dbContext, SqlFunctionMap sqlFunctionMap) {
    this.dbContext = dbContext;
    this.sqlFunctionMap = sqlFunctionMap;
  }

  @Override
  public DbContext getDbContext() {
    return dbContext;
  }

  @Override
  public SqlFunctionMap getSqlFunctionMap() {
    return sqlFunctionMap;
  }

  public SqlBuilder<DefaultStatementFactory, Select, SelectStatement> getSqlBuilder(Select query) {
    return new SelectTSqlBuilder(this, query);
  }

  @Override
  public SelectStatement getSelect(Select query) {
    return getSqlBuilder(query).build();
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
        && sqlFunctionMap.equals(that.sqlFunctionMap);
  }

  @Override
  public int hashCode() {
    int result = dbContext.hashCode();
    result = 31 * result + sqlFunctionMap.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultStatementFactory{"
        + "dbContext=" + dbContext
        + ", sqlFunctionMap=" + sqlFunctionMap
        + '}';
  }
}
