package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Builder class used for construction of select statements.
 */
@SuppressWarnings("UnusedReturnValue")
public interface SelectBuilder {

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
     * @param column is name of table column to be assigned to column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlName column);

    /**
     * Add column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlName column, SqlName alias);

    /**
     * Add column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlTableAlias tableAlias, SqlName column, SqlName alias);

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
     * Add column with table alias, column name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param alias is alias to be used for column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(String tableAlias, String columnName, String alias);

    /**
     * Add new column; no alias is created, meaning column name will be used instead
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(String tableAlias, String columnName);

    /**
     * Add column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String columnSql);

    /**
     * Add column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String sql, String alias);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String sql, String alias, BindVariable... binds);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String sql, String alias, Collection<BindVariable> binds);

    /**
     * Add table to from clause of the statement
     *
     * @param table is table definition (potentially with join condition)
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(SqlFrom table);

    /**
     * Create from clause based on table
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(SqlName tableName, SqlTableAlias alias);

    /**
     * Create from clause based on table; String version
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(String tableName, String alias);

    /**
     * Create from clause based on Sql expression
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder fromSql(String sqlSelect, SqlTableAlias alias);

    /**
     * Create from clause based on Sql expression; String version
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder fromSql(String sqlSelect, String alias);

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(Select select, SqlTableAlias alias);

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(Select select, String alias);

    /**
     * Add from clause for pseudo-table dual
     *
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder fromDual();

    /**
     * Add where condition
     *
     * @param where is sql where condition to be added
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereSql(SqlWhere where);

    /**
     * Add where condition
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereSql(String conditionSql);

    /**
     * Add where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereSql(String conditionSql, BindVariable... binds);

    /**
     * Add where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereSql(String conditionSql, Collection<BindVariable> binds);

    /**
     * Add multiple conditions combined using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereAnd(SqlWhere... whereConditions);

    /**
     * Add multiple conditions combined using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereAnd(Collection<SqlWhere> whereConditions);

    /**
     * Add multiple conditions combined using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereOr(SqlWhere... whereConditions);

    /**
     * Add multiple conditions combined using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereOr(Collection<SqlWhere> whereConditions);

    /**
     * Add bind variable. If variable already exists, verifies that supplied value is compatible with existing one and
     * throws and exception if it is not (different type or different values)
     *
     * @param bind is bind variable to be added
     * @return self to allow fluent build
     */
    @Nonnull
    SelectBuilder addBind(BindVariable bind);

    /**
     * Add bind variables
     *
     * @param binds is list of bind variables to be added
     * @return self to allow fluent build
     */
    @Nonnull
    SelectBuilder addBinds(Collection<BindVariable> binds);

    /**
     * Build select statement from builder
     *
     * @return resulting (non-mutable) select statement
     */
    @Nonnull
    Select build();

    /**
     * Create clone of the statement. Does deep copy, but keeps references to non-mutable objects
     *
     * @return clone of this statement
     */
    @Nonnull
    SelectBuilder copy();
}
