package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;
import com.provys.provysdb.dbsqlbuilder.SqlAdmin;

import javax.annotation.Nonnull;

/**
 * Sql builder using admin connection to database (without switching Provys user context)
 */
public class SqlAdminImpl extends SqlBase implements SqlAdmin {

    /**
     * Create Sql interface based on admin (not personalised) connection to database
     *
     * @param dbContext is provys database context connection should be based on
     */
    public SqlAdminImpl(DbContext dbContext) {
        super(dbContext);
    }

    @Nonnull
    @Override
    public DbConnection getConnection() {
        return getDbContext().getConnection();
    }
}
