package com.provys.provysdb.sqlbuilder;

import com.provys.provysdb.dbcontext.DbConnection;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

/**
 * Interface defining root factory class for all objects, used for building Sql statements
 */
public interface Sql {

    /**
     * @return connection to underlying datasource
     */
    @Nonnull
    DbConnection getConnection();

    /**
     * @return select statement builder
     */
    @Nonnull
    SelectBuilder select();

    /**
     * @param value is value of literal
     * @return varchar literal
     */
    @Nonnull
    LiteralT<String> literal(String value);

    /**
     * @param value is value of literal
     * @return NVarchar literal
     */
    @Nonnull
    LiteralT<String> literalNVarchar(String value);

    /**
     * @param value is value of literal
     * @return byte literal
     */
    @Nonnull
    LiteralT<Byte> literal(byte value);

    /**
     * @param value is value of literal
     * @return short literal
     */
    @Nonnull
    LiteralT<Short> literal(short value);

    /**
     * @param value is value of literal
     * @return integer literal
     */
    @Nonnull
    LiteralT<Integer> literal(int value);

    /**
     * @param value is value of literal
     * @return long literal
     */
    @Nonnull
    LiteralT<Long> literal(long value);

    /**
     * @param value is value of literal
     * @return BigInteger literal
     */
    @Nonnull
    LiteralT<BigInteger> literal(BigInteger value);

    /**
     * @param value is value of literal
     * @return float literal
     */
    @Nonnull
    LiteralT<Float> literal(float value);

    /**
     * @param value is value of literal
     * @return double literal
     */
    @Nonnull
    LiteralT<Double> literal(double value);

    /**
     * @param value is value of literal
     * @return BigDecimal literal
     */
    @Nonnull
    LiteralT<BigDecimal> literal(BigDecimal value);

    /**
     * Create Sql name object based on supplied text. Validates name during creation.
     *
     * @param name to be created
     * @return created name object
     */
    @Nonnull
    SqlIdentifier name(String name);

    /**
     * Create column with given name
     *
     * @param column is name of table column to be assigned to column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlIdentifier column);

    /**
     * Create column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlIdentifier column, SqlIdentifier alias);

    /**
     * Create column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlTableAlias tableAlias, SqlIdentifier column);

    /**
     * Create column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return created column
     */
    @Nonnull
    SqlColumn column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias);

    /**
     * Create new column
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param alias is alias to be used for column
     * @return created column
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
     * @return created column
     */
    @Nonnull
    SqlColumn column(String tableAlias, String columnName);

    /**
     * Create column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String columnSql);

    /**
     * Create column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias, BindVariable... binds);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias, Collection<BindVariable> binds);

    /**
     * Create Sql table alias object based on supplied text. Validates text during creation.
     *
     * @param tableAlias is alias (String value)
     * @return created alias object
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
    SqlFrom from(SqlIdentifier tableName, SqlTableAlias alias);

    /**
     * Create from clause based on table; String version
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
    SqlFrom fromSql(String sqlSelect, SqlTableAlias alias);

    /**
     * Create from clause based on Sql expression; String version
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
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return created from clause
     */
    @Nonnull
    SqlFrom from(Select select, SqlTableAlias alias);

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return created from clause
     */
    @Nonnull
    SqlFrom from(Select select, String alias);

    /**
     * Create from clause for pseudo-table dual
     *
     * @return clause fro pseudo-table dual
     */
    @Nonnull
    SqlFrom fromDual();

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
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereSql(String conditionSql, BindVariable... binds);

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    SqlWhere whereSql(String conditionSql, Collection<BindVariable> binds);

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
    SqlWhere whereAnd(Collection<SqlWhere> whereConditions);

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
    SqlWhere whereOr(Collection<SqlWhere> whereConditions);

    /**
     * Create and return joiner, used to combine multiple conditions using OR operator
     *
     * @return created joiner
     */
    @Nonnull
    SqlWhereJoiner whereOrJoiner();
}
