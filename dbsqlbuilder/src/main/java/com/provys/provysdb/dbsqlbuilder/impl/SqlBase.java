package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import com.provys.provysdb.dbsqlbuilder.DbSelectBuilderT0;
import com.provys.provysdb.dbsqlbuilder.DbSql;
import com.provys.provysdb.sqlbuilder.impl.SqlImpl;
import com.provys.provysdb.sqlparser.SqlTokenizer;
import com.provys.provysdb.sqlparser.impl.DefaultSqlTokenizer;

import javax.annotation.Nonnull;
import java.util.*;

abstract class SqlBase extends SqlImpl implements DbSql {

    @Nonnull
    private final DbContext dbContext;

    private SqlBase(DbContext dbContext, SqlTokenizer tokenizer) {
        super(tokenizer);
        this.dbContext = Objects.requireNonNull(dbContext);
    }

    SqlBase(DbContext dbContext) {
        this(dbContext, new DefaultSqlTokenizer());
    }

    @Nonnull
    DbContext getDbContext() {
        return dbContext;
    }

    @Nonnull
    @Override
    public SqlTypeMap getSqlTypeMap() {
        return dbContext.getSqlTypeMap();
    }

    @Nonnull
    @Override
    public DbSelectBuilderT0 select() {
        return new DbSelectBuilderT0Impl(this);
    }
}
