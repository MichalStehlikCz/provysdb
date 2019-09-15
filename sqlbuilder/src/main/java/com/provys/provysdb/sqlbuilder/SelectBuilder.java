package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;

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
     * Add table to from clause of the statement
     *
     * @param table is table definition (potentially with join condition)
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(SqlFrom table);

    /**
     * Add table to from clause of the statement
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(String tableName, String alias);

    /**
     * Add sql expression to from clause of the statement
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
     * @param sqlSelect is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder from(Select sqlSelect, String alias);

    /**
     * Add where condition. Multiple where conditions are combined using AND
     *
     * @param condition is condition to be added
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder where(SqlWhere condition);

    /**
     * Add where condition. Multiple where conditions are combined using AND
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereSql(String conditionSql);

    /**
     * Add where condition with binds. Multiple where conditions are combined using AND
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param bindVariable is list of bind variables, associated with condition
     * @return self to support fluent build
     */
    @Nonnull
    SelectBuilder whereSql(String conditionSql, BindVariable... bindVariable);

    /**
     * Build {@code SqlStatement} from this builder
     */
    @Nonnull
    Select build();

    /**
     * Return alias corresponding to given alias text; used to replace {@code <<int>>} strings with actual values
     *
     * @param tableAlias is text supplied for lookup
     * @return string corresponding to supplied alias
     */
    @Nonnull
    String getTableAlias(SqlTableAlias tableAlias);
}
