package com.provys.provysdb.dbsqlbuilder;

import com.provys.provysdb.sqlbuilder.SelectBuilderGen;

/**
 * Basic version of database backed select builder, with no information about columns and their
 * types.
 */
public interface DbSelectBuilder extends DbSelectBuilderBase, SelectBuilderGen<DbSelectBuilder> {

}
