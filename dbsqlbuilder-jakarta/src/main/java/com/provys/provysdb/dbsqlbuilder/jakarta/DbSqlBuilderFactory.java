package com.provys.provysdb.dbsqlbuilder.jakarta;

import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbsqlbuilder.impl.SqlAdminImpl;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
class DbSqlBuilderFactory {

    @Produces
    @ApplicationScoped
    @Nonnull
    SqlAdminImpl getSqlAdmin(DbContext dbContext) {
        return new SqlAdminImpl(dbContext);
    }
}
