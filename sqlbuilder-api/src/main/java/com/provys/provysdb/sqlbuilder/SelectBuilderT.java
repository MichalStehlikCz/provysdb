package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

public interface SelectBuilderT<T extends SelectBuilderT> {

    /**
     * @return list of already defined columns
     */
    @Nonnull
    List<SqlColumn> getColumns();

    /**
     * Add column to list of columns; it is expected to come from last item, added to from clause. If no items were
     * added to from clause, column is added as is, without table alias
     *
     * @param column is name of table column to be assigned to column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlIdentifier column);

    /**
     * Add column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlIdentifier column, SqlIdentifier alias);

    /**
     * Add column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias);

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
    SelectBuilder columnDirect(String columnSql);

    /**
     * Add column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnDirect(String sql, String alias);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnDirect(String sql, String alias, BindName... binds);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnDirect(String sql, String alias, List<BindName> binds);

    /**
     * Add column with given SQL text, parse text for binds
     *
     * @param columnSql is text that will be used as column definition
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String columnSql);

    /**
     * Add column with given SQL text and alias, parse text for binds
     *
     * @param columnSql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String columnSql, String alias);

    /**
     * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to specify types and
     * values
     *
     * @param columnSql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String columnSql, String alias, BindVariable... binds);

    /**
     * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to specify types and
     * values
     *
     * @param columnSql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder columnSql(String columnSql, String alias, Iterable<BindVariable> binds);

    /**
     * Add table to from clause of the statement
     *
     * @param table is table definition (potentially with join condition)
     * @return self to support fluent build
     */
    @Nonnull
    T from(SqlFrom table);

    /**
     * Create from clause based on table
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    T from(SqlIdentifier tableName, SqlTableAlias alias);

    /**
     * Create from clause based on table; String version
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    T from(String tableName, String alias);

    /**
     * Create from clause based on Sql expression
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    T fromDirect(String sqlSelect, SqlTableAlias alias);

    /**
     * Create from clause based on Sql expression; String version
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    T fromDirect(String sqlSelect, String alias);

    /**
     * Create from clause based on Sql expression
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    T fromSql(String sqlSelect, SqlTableAlias alias);

    /**
     * Create from clause based on Sql expression; String version
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    T fromSql(String sqlSelect, String alias);

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return self to support fluent build
     */
    @Nonnull
    T from(Select select, SqlTableAlias alias);

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return self to support fluent build
     */
    @Nonnull
    T from(Select select, String alias);

    /**
     * Add from clause for pseudo-table dual
     *
     * @return self to support fluent build
     */
    @Nonnull
    T fromDual();

    /**
     * Add where condition
     *
     * @param where is sql where condition to be added
     * @return self to support fluent build
     */
    @Nonnull
    T where(Condition where);

    /**
     * Add where condition
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return self to support fluent build
     */
    @Nonnull
    T whereDirect(String conditionSql);

    /**
     * Add where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    T whereDirect(String conditionSql, BindName... binds);

    /**
     * Add where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    T whereDirect(String conditionSql, List<BindName> binds);

    /**
     * Add where condition
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return self to support fluent build
     */
    @Nonnull
    T whereSql(String conditionSql);

    /**
     * Add where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    T whereSql(String conditionSql, BindVariable... binds);

    /**
     * Add where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    T whereSql(String conditionSql, Iterable<BindVariable> binds);

    /**
     * Add multiple conditions combined using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    T whereAnd(Condition... whereConditions);

    /**
     * Add multiple conditions combined using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    T whereAnd(Collection<Condition> whereConditions);

    /**
     * Add multiple conditions combined using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    T whereOr(Condition... whereConditions);

    /**
     * Add multiple conditions combined using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return self to support fluent build
     */
    @Nonnull
    T whereOr(Collection<Condition> whereConditions);

    /**
     * Build select from builder
     *
     * @return resulting (non-mutable) select statement
     */
    @Nonnull
    Select build();

    /**
     * Build select statement from builder
     *
     * @return select statement based on builder's content
     */
    @Nonnull
    SelectStatement prepare();

    /**
     * Create clone of the statement. Does deep copy, but keeps references to non-mutable objects
     *
     * @return clone of this statement
     */
    @Nonnull
    T copy();
}
