package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Sql builder using supplied token to switch Provys user
 */
public class SqlToken extends SqlBase {

    private final String dbToken;

    SqlToken(DbContext dbContext, String dbToken) {
        super(dbContext);
        this.dbToken = Objects.requireNonNull(dbToken);
    }

    @Nonnull
    @Override
    public DbConnection getConnection() {
        return getDbContext().getConnection(dbToken);
    }
}
