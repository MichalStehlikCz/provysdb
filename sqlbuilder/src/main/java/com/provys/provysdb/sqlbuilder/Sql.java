package com.provys.provysdb.sqlbuilder;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Common ancestor for database contexts, used for building statements and their parts.
 * Child interfaces add option to build whole statement, either database connected or disconnected
 */
public interface Sql {
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
     * @param value is value of literal
     * @return DtUid literal
     */
    @Nonnull
    LiteralT<DtUid> literal(DtUid value);

    /**
     * @param value is value of literal
     * @return date literal
     */
    @Nonnull
    LiteralT<DtDate> literal(DtDate value);

    /**
     * @param value is value of literal
     * @return datetime literal
     */
    @Nonnull
    LiteralT<DtDateTime> literal(DtDateTime value);

    /**
     * Create bind name based on supplied String
     *
     * @param name is name of bind value, case insensitive
     * @return bind name
     */
    BindName bind(String name);

    /**
     * Create bind value based on supplied (non-null) value. Note that bind will take type from supplied value. This is
     * convenient if you control type of supplied value or you do not want to bind different value to the same statement
     * in future, but in many situations, you might want to consider variant with explicit specification of bind type
     *
     * @param name is name of bind value, case insensitive
     * @param value is value bind will get assigned; it is also used to infer type
     * @param <T> is type of bind value
     * @return bind value with supplied name and value
     */
    <T> BindValueT<T> bind(String name, T value);

    /**
     * Create bind value of supplied type based on supplied value; value might be null
     *
     * @param name is name of bind value, case insensitive
     * @param value is value bind will get assigned
     * @param clazz defines type of bind value
     * @param <T> is type of bind value
     * @return bind value of given type with supplied name and value
     */
    <T> BindValueT<T> bind(String name, @Nullable T value, Class<T> clazz);

    /**
     * Create bind value based on supplied type, without value; value is set to null and should be specified later.
     *
     * @param name is name of bind value, case insensitive
     * @param clazz is type of bind value
     * @param <T> is type of bind value
     * @return bind value with supplied name and type
     */
    default <T> BindValueT<T> bindEmpty(String name, Class<T> clazz) {
        return bind(name, null, clazz);
    }

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
     * Create new column; no alias is created, meaning column name will be sued instead and no table spec risking
     * ambiguity if more tables are joined
     *
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @return created column
     */
    @Nonnull
    SqlColumn column(String columnName);

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
     * Create column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @return created column
     */
    @Nonnull
    SqlColumn columnDirect(String columnSql);

    /**
     * Create column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return created column
     */
    @Nonnull
    SqlColumn columnDirect(String sql, String alias);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column
     * @return created column
     */
    @Nonnull
    SqlColumn columnDirect(String sql, String alias, BindName... binds);

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column, in proper oder, binds should be referenced using Java conventions
     *             (e.g. using ? as placeholder)
     * @return created column
     */
    @Nonnull
    SqlColumn columnDirect(String sql, String alias, List<BindName> binds);

    /**
     * Create column with given SQL text and parse it for bind variables, expressed using :name notation
     *
     * @param columnSql is text that will be used as column definition
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String columnSql);

    /**
     * Create column with given SQL text and alias and parse it for bind variables, expressed using :name notation
     *
     * @param sql is text that will be used as column definition. Binds are parsed from text
     * @param alias is text that will be used as alias for new column
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias);

    /**
     * Add column with given SQL text, alias and parse it for bind variables, expressed using :name notation; bind
     * variables can be supplied to assign value and type to binds
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias, BindValue... binds);

    /**
     * Add column with given SQL text, alias and parse it for bind variables, expressed using :name notation; bind
     * variables can be supplied to assign value and type to binds
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column, in proper oder, binds should be referenced using Java conventions
     *             (e.g. using ? as placeholder)
     * @return created column
     */
    @Nonnull
    SqlColumn columnSql(String sql, String alias, Iterable<BindValue> binds);

    /**
     * Create mandatory column with given name
     *
     * @param column is name of table column to be assigned to column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(SqlIdentifier column, Class<T> clazz);

    /**
     * Create mandatory column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz);

    /**
     * Create mandatory column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column, Class<T> clazz);

    /**
     * Create mandatory column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz);

    /**
     * Create new mandatory column; it is created without table alias, risking ambiguity
     *
     * @param columnName is name of column. It must be valid column name (in "" or first character letter and remaining
     *                  letters, numbers and characters $ and #). Use columnSql to add columns based on sql expressions
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(String columnName, Class<T> clazz);

    /**
     * Create new mandatory column; no alias is created, meaning column name will be sued instead
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(String tableAlias, String columnName, Class<T> clazz);

    /**
     * Create new mandatory column
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param alias is alias to be used for column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> column(String tableAlias, String columnName, String alias, Class<T> clazz);

    /**
     * Create mandatory column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnDirect(String columnSql, Class<T> clazz);

    /**
     * Create mandatory column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz);

    /**
     * Add mandatory column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param binds is list of binds used in column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz, BindName... binds);

    /**
     * Add mandatory column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column, in proper oder, binds should be referenced using Java conventions
     *             (e.g. using ? as placeholder)
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz);

    /**
     * Create mandatory column with given SQL text and parse it for bind variables, expressed using :name notation
     *
     * @param columnSql is text that will be used as column definition
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnSql(String columnSql, Class<T> clazz);

    /**
     * Create mandatory column with given SQL text and alias and parse it for bind variables, expressed using :name
     * notation
     *
     * @param sql is text that will be used as column definition. Binds are parsed from text
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz);

    /**
     * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed using :name notation;
     * bind variables can be supplied to assign value and type to binds
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param binds is list of binds used in column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz, BindValue... binds);

    /**
     * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed using :name notation;
     * bind variables can be supplied to assign value and type to binds
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column, in proper oder, binds should be referenced using Java conventions
     *             (e.g. using ? as placeholder)
     * @param clazz is type of return value of column
     * @return created column
     * @param <T> is Java type corresponding to values in given column
     */
    @Nonnull
    <T> SqlColumnT<T> columnSql(String sql, String alias, Iterable<BindValue> binds, Class<T> clazz);

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
     * Create from clause based on Sql expression, directly passed to evaluation without parsing
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    SqlFrom fromDirect(String sqlSelect, SqlTableAlias alias);

    /**
     * Create from clause based on Sql expression, directly pass to evaluation without parsing; alias as String version
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    SqlFrom fromDirect(String sqlSelect, String alias);

    /**
     * Create from clause based on Sql expression; parse expression to retrieve binds
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    SqlFrom fromSql(String sqlSelect, SqlTableAlias alias);

    /**
     * Create from clause based on Sql expression; parse expression to retrieve binds
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
    Condition conditionDirect(String conditionSql);

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    Condition conditionDirect(String conditionSql, BindName... binds);

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    Condition conditionDirect(String conditionSql, List<BindName> binds);

    /**
     * Create where condition; parse supplied string to retrieve bind variables
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return created where condition
     */
    @Nonnull
    Condition conditionSql(String conditionSql);

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind variables. Condition
     *                    will be surrounded by brackets before adding to statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    Condition conditionSql(String conditionSql, BindValue... binds);

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind variables. Condition
     *                    will be surrounded by brackets before adding to statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    Condition conditionSql(String conditionSql, Iterable<BindValue> binds);

    /**
     * Combine multiple conditions using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    Condition conditionAnd(Condition... whereConditions);

    /**
     * Combine multiple conditions using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    Condition conditionAnd(Collection<Condition> whereConditions);

    /**
     * Create and return joiner, used to combine multiple conditions using AND operator
     *
     * @return created joiner
     */
    @Nonnull
    ConditionJoiner conditionAndJoiner();

    /**
     * Combine multiple conditions using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    Condition conditionOr(Condition... whereConditions);

    /**
     * Combine multiple conditions using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    Condition conditionOr(Collection<Condition> whereConditions);

    /**
     * Create and return joiner, used to combine multiple conditions using OR operator
     *
     * @return created joiner
     */
    @Nonnull
    ConditionJoiner conditionOrJoiner();

    /**
     * Create equals comparison {@code (first = second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @return created comparison (boolean expression / condition)
     * @param <T> is type of operands in comparison
     */
    @Nonnull
    <T> Condition eq(ExpressionT<T> first, ExpressionT<T> second);

    /**
     * Create not-equal comparison {@code (first != second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @return created comparison (boolean expression / condition)
     * @param <T> is type of operands in comparison
     */
    @Nonnull
    <T> Condition notEq(ExpressionT<T> first, ExpressionT<T> second);

    /**
     * Create less than comparison {@code (first < second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @return created comparison (boolean expression / condition)
     * @param <T> is type of operands in comparison
     */
    @Nonnull
    <T> Condition lessThan(ExpressionT<T> first, ExpressionT<T> second);

    /**
     * Create less or equal comparison {@code (first <= second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @return created comparison (boolean expression / condition)
     * @param <T> is type of operands in comparison
     */
    @Nonnull
    <T> Condition lessOrEqual(ExpressionT<T> first, ExpressionT<T> second);

    /**
     * Create greater than comparison {@code (first > second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @return created comparison (boolean expression / condition)
     * @param <T> is type of operands in comparison
     */
    @Nonnull
    <T> Condition greaterThan(ExpressionT<T> first, ExpressionT<T> second);

    /**
     * Create greater or equal comparison {@code (first >= second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @return created comparison (boolean expression / condition)
     * @param <T> is type of operands in comparison
     */
    @Nonnull
    <T> Condition greaterOrEqual(ExpressionT<T> first, ExpressionT<T> second);

    /**
     * Create is null expression {@code (first IS NULL)}
     *
     * @param first is parameter of IS NULL expression
     * @return created expression (boolean expression / condition)
     * @param <T> is type of operand in expression
     */
    @Nonnull
    <T> Condition isNull(ExpressionT<T> first);
}
