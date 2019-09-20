package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.DbContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.mockito.Mockito.*;

/**
 * Implementation of Sql interface using SqlBase, that mocks database connection; calls to retrieve connection will fail
 * unless (likely mocked) connection is supplied during construction
 */
public class SqlTest extends SqlBase {

    @Nullable
    private final DbConnection connection;

    SqlTest() {
        super(mock(DbContext.class));
        connection = null;
    }

    SqlTest(DbConnection connection) {
        super(mock(DbContext.class));
        this.connection = connection;
    }

    @Nonnull
    @Override
    public DbConnection getConnection() {
        throw new RuntimeException("Not implemented");
    }
}
