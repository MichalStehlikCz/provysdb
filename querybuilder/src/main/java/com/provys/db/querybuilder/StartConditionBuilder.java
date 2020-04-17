package com.provys.db.querybuilder;

/**
 * Interface represents start of condition - allows to continue building condition as AND or OR
 * connected chain of conditions.
 */
public interface StartConditionBuilder extends AndConditionBuilder, OrConditionBuilder {

}
