package com.provys.provysdb.dbsqlbuilder;

import com.provys.provysdb.dbcontext.DbConnection;
import com.provys.provysdb.dbcontext.SqlTypeMap;
import com.provys.provysdb.sqlbuilder.Sql;

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
     * @return used sql type adapter map (taken from underlying datasource)
     */
    @Nonnull
    SqlTypeMap getSqlTypeMap();

    /**
     * @return select statement builder
     */
    @Nonnull
    DbSelectBuilderT0 select();
}
