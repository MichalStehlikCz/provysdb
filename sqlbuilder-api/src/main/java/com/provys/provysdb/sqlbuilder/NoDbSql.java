package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Sql context for non-database applications.
 * While database connected applications should generally use objects from dbsqlbuilder package (SqlAdmin and SqlUser),
 * non-database applications (ones that use other services to access data from database) can inject instance for this
 * interface to produce builder that can build select statements without the use of connection pool
 */
public interface NoDbSql extends Sql {

    /**
     * @return select statement builder
     */
    @Nonnull
    SelectBuilderT0 select();
}
