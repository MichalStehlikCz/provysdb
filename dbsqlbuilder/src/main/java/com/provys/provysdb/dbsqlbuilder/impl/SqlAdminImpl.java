package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbsqlbuilder.SqlAdmin;

import javax.annotation.Nonnull;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Sql builder using admin connection to database (without switching Provys user context)
 */
@ApplicationScoped
class SqlAdminImpl extends SqlBase implements SqlAdmin {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    SqlAdminImpl(DbContext dbContext) {
        super(dbContext);
    }

    @Nonnull
    @Override
    public DbConnection getConnection() {
        return getDbContext().getConnection();
    }
}
