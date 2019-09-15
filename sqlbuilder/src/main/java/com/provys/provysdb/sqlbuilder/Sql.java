package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

/**
 * Interface defining root factory class for all objects, used for building Sql statements
 */
public interface Sql {

    /**
     * Create Sql name object based on supplied text. Validates name during creation.
     *
     * @return created name object
     */
    @Nonnull
    SqlName name(String name);

    /**
     * Create column with given name
     *
     * @param column is name of table column to be assigned to column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlName column);

    /**
     * Create column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlName column, SqlName alias);

    /**
     * Create column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlTableAlias tableAlias, SqlName column, SqlName alias);

    /**
     * Create new column
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param alias is alias to be used for column
     * @return self to support fluent build
     */
    @Nonnull
    SqlColumn column(String tableAlias, String columnName, String alias);

    /**
     * Create new column; no alias is created, meaning column name will be sued instead
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @return self to support fluent build
     */
    @Nonnull
    SqlColumn column(String tableAlias, String columnName);

    /**
     * Add column with given SQL text to list of columns
     *
     * @param columnSql is text that will be used as column definition
     * @return self to support fluent build
     */
    @Nonnull
    SqlColumn columnSql(String columnSql);

    /**
     * Add column with given SQL text and alias to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias);

    /**
     * Create Sql table alias object based on supplied text. Validates text during creation.
     */
    @Nonnull
    SqlTableAlias tableAlias(String tableAlias);

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
