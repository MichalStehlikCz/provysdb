package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT0;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.impl.SqlImpl;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;
import java.util.Objects;

abstract class SqlBase extends SqlImpl implements DbSql {

  private final DbContext dbContext;

  private SqlBase(DbContext dbContext, SqlTokenizer tokenizer) {
    super(tokenizer);
    this.dbContext = Objects.requireNonNull(dbContext);
  }

  SqlBase(DbContext dbContext) {
    this(dbContext, new DefaultSqlTokenizer());
  }

  DbContext getDbContext() {
    return dbContext;
  }

  @Override
  public SqlTypeMap getSqlTypeMap() {
    return dbContext.getSqlTypeMap();
  }

  @Override
  public DbSelectBuilderT0 select() {
    return new DbSelectBuilderT0Impl(this);
  }

  @Override
  public String toString() {
    return "SqlBase{"
        + "dbContext=" + dbContext
        + ", " + super.toString() + '}';
  }
}
