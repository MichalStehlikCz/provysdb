package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Builder class used for construction of select statements.
 */
@SuppressWarnings("UnusedReturnValue")
public interface SelectBuilderGen<U extends SelectBuilderGen<U>> extends SelectBuilderBase<U, U> {

    /**
     * Add predefined column to list of columns
     *
     * @param column is definition of column to be added to statement
     * @return self to support fluent build
     */
    @Nonnull
    U column(SqlColumn column);

    /**
     * Add column to list of columns; it is expected to come from last item, added to from clause. If no items were
     * added to from clause, column is added as is, without table alias
     *
     * @param column is name of table column to be assigned to column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U column(SqlIdentifier column, Class<T> clazz);

    /**
     * Add column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz);

    /**
     * Add column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz);

    /**
     * Add column to list of columns; it is expected to come from last item, added to from clause. If no items were
     * added to from clause, column is added as is, without table alias
     *
     * @param columnName is name of column; it should be column in last item, added to from clause. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U column(String columnName, Class<T> clazz);

    /**
     * Add new column; no alias is created, meaning column name will be used instead
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U column(String tableAlias, String columnName, Class<T> clazz);

    /**
     * Add column with table alias, column name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param alias is alias to be used for column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U column(String tableAlias, String columnName, String alias, Class<T> clazz);

    /**
     * Add column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnDirect(String columnSql, Class<T> clazz);

    /**
     * Add column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnDirect(String sql, String alias, Class<T> clazz);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param binds is list of binds, associated with given column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnDirect(String sql, String alias, Class<T> clazz, BindName... binds);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds, associated with given column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz);

    /**
     * Add column with given SQL text, parse text for binds
     *
     * @param columnSql is text that will be used as column definition
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnSql(String columnSql, Class<T> clazz);

    /**
     * Add column with given SQL text and alias, parse text for binds
     *
     * @param columnSql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnSql(String columnSql, String alias, Class<T> clazz);

    /**
     * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to specify types and
     * values
     *
     * @param columnSql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param binds is list of binds, associated with given column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnSql(String columnSql, String alias, Class<T> clazz, BindValue... binds);

    /**
     * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to specify types and
     * values
     *
     * @param columnSql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds, associated with given column
     * @param clazz is type of return value of column
     * @return self to support fluent build
     * @param <T> is type of column being added
     */
    @Nonnull
    <T> U columnSql(String columnSql, String alias, Iterable<BindValue> binds, Class<T> clazz);
}
