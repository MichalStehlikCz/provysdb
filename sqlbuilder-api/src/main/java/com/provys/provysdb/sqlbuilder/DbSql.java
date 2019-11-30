package com.provys.provysdb.sqlbuilder;

import com.provys.provysdb.dbcontext.DbConnection;

import javax.annotation.Nonnull;

/**
 * Interface defining root factory class for all objects, used for building Sql statements
 */
public interface DbSql extends Sql {

    /**
     * @return connection to underlying datasource
     */
    @Nonnull
    DbConnection getConnection();

    /**
     * @return select statement builder
     */
    @Nonnull
    SelectBuilder select();

}
