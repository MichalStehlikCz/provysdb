package com.provys.provysdb.dbsqlbuilder;

/**
 * Interface allows building and running statements in Provys database under administrator (strong)
 * account. When used, it should be always ensured that access rights are verified in higher level.
 */
@SuppressWarnings("MarkerInterface")
// we want to use this marker interface to make bean selection easier (without qualifiers)
public interface SqlAdmin extends DbSql {

}
