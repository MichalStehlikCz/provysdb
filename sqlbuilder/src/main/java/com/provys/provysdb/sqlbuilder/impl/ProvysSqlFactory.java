package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.sqlbuilder.Sql;
import com.provys.provysdb.sqlbuilder.SqlFactory;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

/**
 * Implementation of SqlFactory, built on top of ProvysDbContext
 */
@ApplicationScoped
public class ProvysSqlFactory implements SqlFactory {

    private final DbContext dbContext;

    @Inject
    public ProvysSqlFactory(DbContext dbContext) {
        this.dbContext = Objects.requireNonNull(dbContext);
    }

    @Nonnull
    @Override
    public Sql getSql() {
        return new SqlAdmin(dbContext);
    }

    @Nonnull
    @Override
    public Sql getSql(String dbToken) {
        return new SqlWithToken(dbContext, dbToken);
    }
}
