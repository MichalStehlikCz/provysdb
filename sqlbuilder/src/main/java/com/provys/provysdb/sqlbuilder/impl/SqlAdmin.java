package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;

import javax.annotation.Nonnull;

/**
 * Sql builder using admin connection to database (without switching Provys user context)
 */
class SqlAdmin extends SqlBase {

    SqlAdmin(DbContext dbContext) {
        super(dbContext);
    }

    @Nonnull
    @Override
    public DbConnection getConnection() {
        return getDbContext().getConnection();
    }
}
