package com.provys.provysdb.sqlbuilder;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sqlbuilder.impl.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Factory class, used to produce instances of basic objects (names, identifiers, binds etc.). Recommended to be used
 * via static import or to construct static objects (like table or column accessors). More complex objects (e.g. ones
 * that require parser or use additional parametrisation) are produced with default parser / parametrisation and without
 * database access via this static factory. It is necessary to use instance of {@code Sql} interface implementation that
 * allows proper settings in such situations.
 */
public final class SqlFactory {

    /**
     * Static methods use this dynamically created instance to produce objects in static methods. This enables
     */
    private static final NoDbSql SQL = new NoDbSqlImpl();

    /**
     * @return select statement builder
     */
    @Nonnull
    public static SelectBuilderT0 select() {
        return SQL.select();
    }

    /**
     * @param value is value of literal
     * @return varchar literal
     */
    @Nonnull
    public static LiteralT<String> literal(String value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return NVarchar literal
     */
    @Nonnull
    public static LiteralT<String> literalNVarchar(String value) {
        return SQL.literalNVarchar(value);
    }

    /**
     * @param value is value of literal
     * @return byte literal
     */
    @Nonnull
    public static LiteralT<Byte> literal(byte value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return short literal
     */
    @Nonnull
    public static LiteralT<Short> literal(short value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return integer literal
     */
    @Nonnull
    public static LiteralT<Integer> literal(int value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return long literal
     */
    @Nonnull
    public static LiteralT<Long> literal(long value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return BigInteger literal
     */
    @Nonnull
    public static LiteralT<BigInteger> literal(BigInteger value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return float literal
     */
    @Nonnull
    public static LiteralT<Float> literal(float value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return double literal
     */
    @Nonnull
    public static LiteralT<Double> literal(double value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return BigDecimal literal
     */
    @Nonnull
    public static LiteralT<BigDecimal> literal(BigDecimal value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return DtUid literal
     */
    @Nonnull
    public static LiteralT<DtUid> literal(DtUid value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return date literal
     */
    @Nonnull
    public static LiteralT<DtDate> literal(DtDate value) {
        return SQL.literal(value);
    }

    /**
     * @param value is value of literal
     * @return datetime literal
     */
    @Nonnull
    public static LiteralT<DtDateTime> literal(DtDateTime value) {
        return SQL.literal(value);
    }

    /**
     * Create bind name based on supplied String
     *
     * @param name is name of bind value, case insensitive
     * @return bind name
     */
    public static BindName bind(String name) {
        return SQL.bind(name);
    }

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
    public static <T> BindValueT<T> bind(String name, T value) {
        return SQL.bind(name, value);
    }

    /**
     * Create bind value of supplied type based on supplied value; value might be null
     *
     * @param name is name of bind value, case insensitive
     * @param value is value bind will get assigned
     * @param clazz defines type of bind value
     * @param <T> is type of bind value
     * @return bind value of given type with supplied name and value
     */
    public static <T> BindValueT<T> bind(String name, @Nullable T value, Class<T> clazz) {
        return SQL.bind(name, value, clazz);
    }

    /**
     * Create bind value based on supplied type, without value; value is set to null and should be specified later.
     *
     * @param name is name of bind value, case insensitive
     * @param clazz is type of bind value
     * @param <T> is type of bind value
     * @return bind value with supplied name and type
     */
    public static <T> BindValueT<T> bindEmpty(String name, Class<T> clazz) {
        return SQL.bindEmpty(name, clazz);
    }

    /**
     * Create Sql name object based on supplied text. Validates name during creation.
     *
     * @param name to be created
     * @return created name object
     */
    @Nonnull
    public static SqlIdentifier name(String name) {
        return SQL.name(name);
    }

    /**
     * Create column with given name
     *
     * @param column is name of table column to be assigned to column
     * @return created column
     */
    @Nonnull
    public static SqlTableColumn column(SqlIdentifier column) {
        return SQL.column(column);
    }

    /**
     * Create column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return created column
     */
    @Nonnull
    public static SqlTableColumn column(SqlIdentifier column, SqlIdentifier alias) {
        return SQL.column(column, alias);
    }

    /**
     * Create column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @return created column
     */
    @Nonnull
    public static SqlTableColumn column(SqlTableAlias tableAlias, SqlIdentifier column) {
        return SQL.column(tableAlias, column);
    }

    /**
     * Create column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @return created column
     */
    @Nonnull
    public static SqlTableColumn column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias) {
        return SQL.column(tableAlias, column, alias);
    }

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
    public static SqlTableColumn column(String columnName) {
        return SQL.column(columnName);
    }

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
    public static SqlTableColumn column(String tableAlias, String columnName) {
        return SQL.column(tableAlias, columnName);
    }

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
    public static SqlTableColumn column(String tableAlias, String columnName, String alias) {
        return SQL.column(tableAlias, columnName, alias);
    }

    /**
     * Create column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @return created column
     */
    @Nonnull
    public static SqlColumn columnDirect(String columnSql) {
        return SQL.columnDirect(columnSql);
    }

    /**
     * Create column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @return created column
     */
    @Nonnull
    public static SqlColumn columnDirect(String sql, String alias) {
        return SQL.columnDirect(sql, alias);
    }

    /**
     * Add column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column
     * @return created column
     */
    @Nonnull
    public static SqlColumn columnDirect(String sql, String alias, BindName... binds) {
        return SQL.columnDirect(sql, alias, binds);
    }

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
    public static SqlColumn columnDirect(String sql, String alias, List<BindName> binds) {
        return SQL.columnDirect(sql, alias, binds);
    }

    /**
     * Create column with given SQL text and parse it for bind variables, expressed using :name notation
     *
     * @param columnSql is text that will be used as column definition
     * @return created column
     */
    @Nonnull
    public static SqlColumn columnSql(String columnSql) {
        return SQL.columnSql(columnSql);
    }

    /**
     * Create column with given SQL text and alias and parse it for bind variables, expressed using :name notation
     *
     * @param sql is text that will be used as column definition. Binds are parsed from text
     * @param alias is text that will be used as alias for new column
     * @return created column
     */
    @Nonnull
    public static SqlColumn columnSql(String sql, String alias) {
        return SQL.columnSql(sql, alias);
    }

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
    public static SqlColumn columnSql(String sql, String alias, BindValue... binds) {
        return SQL.columnSql(sql, alias, binds);
    }

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
    public static SqlColumn columnSql(String sql, String alias, Iterable<BindValue> binds) {
        return SQL.columnSql(sql, alias, binds);
    }

    /**
     * Create typed column based on supplied (untyped) column and type
     *
     * @param column is untyped column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> column(SqlColumn column, Class<T> clazz) {
        return SQL.column(column, clazz);
    }

    /**
     * Create typed table column based on supplied (untyped) table column and type
     *
     * @param column is untyped column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(SqlTableColumn column, Class<T> clazz) {
        return SQL.column(column, clazz);
    }

    /**
     * Create mandatory column with given name
     *
     * @param column is name of table column to be assigned to column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(SqlIdentifier column, Class<T> clazz) {
        return SQL.column(column, clazz);
    }

    /**
     * Create mandatory column with given name and alias
     *
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return SQL.column(column, alias, clazz);
    }

    /**
     * Create mandatory column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column, Class<T> clazz) {
        return SQL.column(tableAlias, column, clazz);
    }

    /**
     * Create mandatory column with given table alias, name and alias
     *
     * @param tableAlias is alias of table column is in
     * @param column is name of table column to be assigned to column
     * @param alias is alias to be sued for column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias, Class<T> clazz) {
        return SQL.column(tableAlias, column, alias, clazz);
    }

    /**
     * Create new mandatory column; it is created without table alias, risking ambiguity
     *
     * @param columnName is name of column. It must be valid column name (in "" or first character letter and remaining
     *                  letters, numbers and characters $ and #). Use columnSql to add columns based on sql expressions
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(String columnName, Class<T> clazz) {
        return SQL.column(columnName, clazz);
    }

    /**
     * Create new mandatory column; no alias is created, meaning column name will be sued instead
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(String tableAlias, String columnName, Class<T> clazz) {
        return SQL.column(tableAlias, columnName, clazz);
    }

    /**
     * Create new mandatory column
     *
     * @param tableAlias is alias of table column is in
     * @param columnName is name of column. It must be valid
     *                   column name (in "" or first character letter and remaining letters, numbers and characters $
     *                   and #). Use columnSql to add columns based on sql expressions
     * @param alias is alias to be used for column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlTableColumnT<T> column(String tableAlias, String columnName, String alias, Class<T> clazz) {
        return SQL.column(tableAlias, columnName, alias, clazz);
    }

    /**
     * Create mandatory column with given SQL text
     *
     * @param columnSql is text that will be used as column definition
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnDirect(String columnSql, Class<T> clazz) {
        return SQL.columnDirect(columnSql, clazz);
    }

    /**
     * Create mandatory column with given SQL text and alias
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz) {
        return SQL.columnDirect(sql, alias, clazz);
    }

    /**
     * Add mandatory column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param binds is list of binds used in column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnDirect(String sql, String alias, Class<T> clazz, BindName... binds) {
        return SQL.columnDirect(sql, alias, clazz, binds);
    }

    /**
     * Add mandatory column with given SQL text, alias and binds to list of columns
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column, in proper oder, binds should be referenced using Java conventions
     *             (e.g. using ? as placeholder)
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnDirect(String sql, String alias, List<BindName> binds, Class<T> clazz) {
        return SQL.columnDirect(sql, alias, binds, clazz);
    }

    /**
     * Create mandatory column with given SQL text and parse it for bind variables, expressed using :name notation
     *
     * @param columnSql is text that will be used as column definition
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnSql(String columnSql, Class<T> clazz) {
        return SQL.columnSql(columnSql, clazz);
    }

    /**
     * Create mandatory column with given SQL text and alias and parse it for bind variables, expressed using :name
     * notation
     *
     * @param sql is text that will be used as column definition. Binds are parsed from text
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz) {
        return SQL.columnSql(sql, alias, clazz);
    }

    /**
     * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed using :name notation;
     * bind variables can be supplied to assign value and type to binds
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param clazz is type of return value of column
     * @param binds is list of binds used in column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnSql(String sql, String alias, Class<T> clazz, BindValue... binds) {
        return SQL.columnSql(sql, alias, clazz, binds);
    }

    /**
     * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed using :name notation;
     * bind variables can be supplied to assign value and type to binds
     *
     * @param sql is text that will be used as column definition
     * @param alias is text that will be used as alias for new column
     * @param binds is list of binds used in column, in proper oder, binds should be referenced using Java conventions
     *             (e.g. using ? as placeholder)
     * @param clazz is type of return value of column
     * @param <T> is Java type corresponding to values in given column
     * @return created column
     */
    @Nonnull
    public static <T> SqlColumnT<T> columnSql(String sql, String alias, Iterable<BindValue> binds, Class<T> clazz) {
        return SQL.columnSql(sql, alias, binds, clazz);
    }

    /**
     * Create Sql table alias object based on supplied text. Validates text during creation.
     *
     * @param tableAlias is alias (String value)
     * @return created alias object
     */
    @Nonnull
    public static SqlTableAlias tableAlias(String tableAlias) {
        return SQL.tableAlias(tableAlias);
    }

    /**
     * Create from clause based on table
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom from(SqlIdentifier tableName, SqlTableAlias alias) {
        return SQL.from(tableName, alias);
    }

    /**
     * Create from clause based on table; String version
     *
     * @param tableName is name of table select is from
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom from(String tableName, String alias) {
        return SQL.from(tableName, alias);
    }

    /**
     * Create from clause based on Sql expression, directly passed to evaluation without parsing
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom fromDirect(String sqlSelect, SqlTableAlias alias) {
        return SQL.fromDirect(sqlSelect, alias);
    }

    /**
     * Create from clause based on Sql expression, directly pass to evaluation without parsing; alias as String version
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom fromDirect(String sqlSelect, String alias) {
        return SQL.fromDirect(sqlSelect, alias);
    }

    /**
     * Create from clause based on Sql expression; parse expression to retrieve binds
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom fromSql(String sqlSelect, SqlTableAlias alias) {
        return SQL.fromSql(sqlSelect, alias);
    }

    /**
     * Create from clause based on Sql expression; parse expression to retrieve binds
     *
     * @param sqlSelect is SQL expression used as data source in SQL clause
     * @param alias is alias new table will get
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom fromSql(String sqlSelect, String alias) {
        return SQL.fromSql(sqlSelect, alias);
    }

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom from(Select select, SqlTableAlias alias) {
        return SQL.from(select, alias);
    }

    /**
     * Add sql expression to from clause of the statement
     *
     * @param select is select statement that will be used in from clause
     * @param alias as alias to be assigned to given expression
     * @return created from clause
     */
    @Nonnull
    public static SqlFrom from(Select select, String alias) {
        return SQL.from(select, alias);
    }

    /**
     * Create from clause for pseudo-table dual
     *
     * @return clause fro pseudo-table dual
     */
    @Nonnull
    public static SqlFrom fromDual() {
        return SQL.fromDual();
    }

    /**
     * Create where condition
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionDirect(String conditionSql) {
        return SQL.conditionDirect(conditionSql);
    }

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionDirect(String conditionSql, BindName... binds) {
        return SQL.conditionDirect(conditionSql, binds);
    }

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionDirect(String conditionSql, List<BindName> binds) {
        return SQL.conditionDirect(conditionSql, binds);
    }

    /**
     * Create where condition; parse supplied string to retrieve bind variables
     *
     * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets before adding to
     *                    statement
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionSql(String conditionSql) {
        return SQL.conditionSql(conditionSql);
    }

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind variables. Condition
     *                    will be surrounded by brackets before adding to statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionSql(String conditionSql, BindValue... binds) {
        return SQL.conditionSql(conditionSql, binds);
    }

    /**
     * Create where condition with binds
     *
     * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind variables. Condition
     *                    will be surrounded by brackets before adding to statement
     * @param binds is list of bind variables, associated with condition
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionSql(String conditionSql, Iterable<BindValue> binds) {
        return SQL.conditionSql(conditionSql, binds);
    }

    /**
     * Combine multiple conditions using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionAnd(Condition... whereConditions) {
        return SQL.conditionAnd(whereConditions);
    }

    /**
     * Combine multiple conditions using AND
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionAnd(Collection<Condition> whereConditions) {
        return SQL.conditionAnd(whereConditions);
    }

    /**
     * Create and return joiner, used to combine multiple conditions using AND operator
     *
     * @return created joiner
     */
    @Nonnull
    public static ConditionJoiner conditionAndJoiner() {
        return SQL.conditionAndJoiner();
    }

    /**
     * Combine multiple conditions using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionOr(Condition... whereConditions) {
        return SQL.conditionOr(whereConditions);
    }

    /**
     * Combine multiple conditions using OR
     *
     * @param whereConditions are where conditions to be combined
     * @return created where condition
     */
    @Nonnull
    public static Condition conditionOr(Collection<Condition> whereConditions) {
        return SQL.conditionOr(whereConditions);
    }

    /**
     * Create and return joiner, used to combine multiple conditions using OR operator
     *
     * @return created joiner
     */
    @Nonnull
    public static ConditionJoiner conditionOrJoiner() {
        return SQL.conditionOrJoiner();
    }

    /**
     * Create equals comparison {@code (first = second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @param <T> is type of operands in comparison
     * @return created comparison (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition eq(ExpressionT<T> first, ExpressionT<T> second) {
        return SQL.eq(first, second);
    }

    /**
     * Create not-equal comparison {@code (first != second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @param <T> is type of operands in comparison
     * @return created comparison (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition notEq(ExpressionT<T> first, ExpressionT<T> second) {
        return SQL.notEq(first, second);
    }

    /**
     * Create less than comparison {@code (first < second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @param <T> is type of operands in comparison
     * @return created comparison (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition lessThan(ExpressionT<T> first, ExpressionT<T> second) {
        return SQL.lessThan(first, second);
    }

    /**
     * Create less or equal comparison {@code (first <= second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @param <T> is type of operands in comparison
     * @return created comparison (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition lessOrEqual(ExpressionT<T> first, ExpressionT<T> second) {
        return SQL.lessOrEqual(first, second);
    }

    /**
     * Create greater than comparison {@code (first > second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @param <T> is type of operands in comparison
     * @return created comparison (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition greaterThan(ExpressionT<T> first, ExpressionT<T> second) {
        return SQL.greaterThan(first, second);
    }

    /**
     * Create greater or equal comparison {@code (first >= second)}
     *
     * @param first is first operand
     * @param second is second operand in comparison
     * @param <T> is type of operands in comparison
     * @return created comparison (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition greaterOrEqual(ExpressionT<T> first, ExpressionT<T> second) {
        return SQL.greaterOrEqual(first, second);
    }

    /**
     * Create is null expression {@code (first IS NULL)}
     *
     * @param first is parameter of IS NULL expression
     * @param <T> is type of operand
     * @return created expression (boolean expression / condition)
     */
    @Nonnull
    public static <T> Condition isNull(ExpressionT<T> first) {
        return SQL.isNull(first);
    }

    /**
     * Pure utility class, cannot be instantiated
     */
    @SuppressWarnings("unused")
    private SqlFactory() {}
}