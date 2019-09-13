package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Interface defining root factory class for all objects, used for building Sql statements
 */
public interface Sql {


    /**
     * Add predefined column to list of columns
     *
     * @param column is definition of column to be added to statement
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlColumn column);

    /**
     * Add column to list of columns; it is expected to come from last item, added to from clause. If no items were
     * added to from clause, column is added as is, without table alias
     *
     * @param columnName is name of column; it should be column in last item, added to from clause. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(String columnName);

    /**
     * Add column with given SQL text to list of columns
     *
     * @param columnSql is text that will be used as column definition
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String columnSql);

    /**
     * Add column with given SQL text and alias to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String sql, String alias);

    /**
     * Create from clause based on table
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    SqlFrom from(String tableName, String alias);

    /**
     * Create from clause based on Sql expression
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    SqlFrom fromSql(String sqlSelect, String alias);

    /**
     * Add sql expression to from clause of the statement
     *
     * @param sqlSelect is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return created from clause
     */
    @Nonnull
    SqlFrom from(Select sqlSelect, String alias);

    /**
     * Add where condition. Multiple where conditions are combined using AND
     *
     * @param condition is condition to be added
     * @return created where condition
     */
    @Nonnull
    SqlWhere where(SqlWhere condition);

    /**
     * Create where condition
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereSql(String conditionSql);

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param bindVariable is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereSql(String conditionSql, BindVariable... bindVariable);

    /**
     * Combine multiple conditions using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereAnd(SqlWhere... whereConditions);

    /**
     * Combine multiple conditions using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereAnd(Iterable<SqlWhere> whereConditions);

    /**
     * Create and return joiner, used to combine multiple conditions using AND operator
     *
     * @return created joiner
     */
    @Nonnull
    SqlWhereJoiner whereAndJoiner();

    /**
     * Combine multiple conditions using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereOr(SqlWhere... whereConditions);

    /**
     * Combine multiple conditions using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereOr(Iterable<SqlWhere> whereConditions);

    /**
     * Create and return joiner, used to combine multiple conditions using OR operator
     *
     * @return created joiner
     */
    @Nonnull
    SqlWhereJoiner whereOrJoiner();
}
