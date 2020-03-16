package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.db.dbcontext.DbContext;
import com.provys.db.dbcontext.SqlTypeMap;
import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT0;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import sqlbuilder.impl.SqlImpl;
import sqlparser.SqlTokenizer;
import sqlparser.impl.DefaultSqlTokenizer;
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
