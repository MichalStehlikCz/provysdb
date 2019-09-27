package com.provys.provysdb.sqlbuilder;

/**
 * Builder class for combining multiple conditions using AND or OR operands
 */
public interface SqlWhereJoiner {

    /**
     * Add condition to joiner
     *
     * @param sqlWhere is condition to be added
     * @return self to allow fluent build
     */
    SqlWhereJoiner add(SqlWhere sqlWhere);

    /**
     * Build condition from joined conditions
     *
     * @return resulting combined (constant) where condition
     */
    SqlWhere build();
}
