package com.provys.provysdb.sqlbuilder;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Common ancestor for database contexts, factory class for building statements and their parts.
 * Child interfaces add option to build whole statement, either database connected or disconnected
 */
public interface Sql {

  /**
   * String literal.
   *
   * @param value is value of literal
   * @return varchar literal
   */
  Literal<String> literal(String value);

  /**
   * Byte literal.
   *
   * @param value is value of literal
   * @return byte literal
   */
  Literal<Byte> literal(byte value);

  /**
   * Short literal.
   *
   * @param value is value of literal
   * @return short literal
   */
  Literal<Short> literal(short value);

  /**
   * Integer literal.
   *
   * @param value is value of literal
   * @return integer literal
   */
  Literal<Integer> literal(int value);

  /**
   * Long literal.
   *
   * @param value is value of literal
   * @return long literal
   */
  Literal<Long> literal(long value);

  /**
   * BigInteger literal.
   *
   * @param value is value of literal
   * @return BigInteger literal
   */
  Literal<BigInteger> literal(BigInteger value);

  /**
   * Float literal.
   *
   * @param value is value of literal
   * @return float literal
   */
  Literal<Float> literal(float value);

  /**
   * Double literal.
   *
   * @param value is value of literal
   * @return double literal
   */
  Literal<Double> literal(double value);

  /**
   * BigDecimal literal.
   *
   * @param value is value of literal
   * @return BigDecimal literal
   */
  Literal<BigDecimal> literal(BigDecimal value);

  /**
   * Boolean literal.
   *
   * @param value is value of literal. Note that Java boolean is translated to Y/N char
   * @return boolean literal
   */
  Literal<Boolean> literal(boolean value);

  /**
   * DtUid literal.
   *
   * @param value is value of literal
   * @return DtUid literal
   */
  Literal<DtUid> literal(DtUid value);

  /**
   * DtDate literal.
   *
   * @param value is value of literal
   * @return date literal
   */
  Literal<DtDate> literal(DtDate value);

  /**
   * DtDateTime literal.
   *
   * @param value is value of literal
   * @return datetime literal
   */
  Literal<DtDateTime> literal(DtDateTime value);

  /**
   * String literal represented as NVARCHAR2.
   *
   * @param value is value of literal
   * @return NVarchar literal
   */
  Literal<String> literalNVarchar(String value);

  /**
   * Create bind name based on supplied String.
   *
   * @param name is name of bind value, case insensitive
   * @return bind name
   */
  BindName bind(String name);

  /**
   * Create bind value based on supplied (non-null) value. Note that bind will take type from
   * supplied value. This is convenient if you control type of supplied value or you do not want to
   * bind different value to the same statement in future, but in many situations, you might want to
   * consider variant with explicit specification of bind type
   *
   * @param bindName is bind name used to produce bind value
   * @param value    is value bind will get assigned; it is also used to infer type
   * @param <T>      is type of bind value
   * @return bind value with supplied name and value
   */
  <T> BindValue<T> bind(BindName bindName, @NonNull T value);

  /**
   * Create bind value based on supplied (non-null) value. Note that bind will take type from
   * supplied value. This is convenient if you control type of supplied value or you do not want to
   * bind different value to the same statement in future, but in many situations, you might want to
   * consider variant with explicit specification of bind type
   *
   * @param name  is name of bind value, case insensitive
   * @param value is value bind will get assigned; it is also used to infer type
   * @param <T>   is type of bind value
   * @return bind value with supplied name and value
   */
  <T> BindValue<T> bind(String name, @NonNull T value);

  /**
   * Create bind value of supplied type based on supplied value. Value might be null in this variant
   * as it is not used to infer type of bind variable.
   *
   * @param bindName is name of bind value, case insensitive
   * @param value    is value bind will get assigned
   * @param clazz    defines type of bind value
   * @param <T>      is type of bind value
   * @return bind value of given type with supplied name and value
   */
  <T> BindValue<T> bind(BindName bindName, @Nullable T value, Class<T> clazz);

  /**
   * Create bind value of supplied type based on supplied value. Value might be null in this variant
   * as it is not used to infer type of bind variable.
   *
   * @param name  is name of bind value, case insensitive
   * @param value is value bind will get assigned
   * @param clazz defines type of bind value
   * @param <T>   is type of bind value
   * @return bind value of given type with supplied name and value
   */
  <T> BindValue<T> bind(String name, @Nullable T value, Class<T> clazz);

  /**
   * Create bind value based on supplied type, without value; value is set to null and should be
   * specified later.
   *
   * @param name  is name of bind value, case insensitive
   * @param clazz is type of bind value
   * @param <T>   is type of bind value
   * @return bind value with supplied name and type
   */
  default <T> BindValue<T> bindEmpty(String name, Class<T> clazz) {
    return bind(name, null, clazz);
  }

  /**
   * Create Sql name object based on supplied text. Validates name during creation.
   *
   * @param name to be created
   * @return created name object
   */
  SqlIdentifier name(String name);

  /**
   * Create column with given name.
   *
   * @param column is name of table column to be assigned to column
   * @return created column
   */
  SqlTableColumn<Object> column(SqlIdentifier column);

  /**
   * Create column with given table alias and name.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @return created column
   */
  SqlTableColumn<Object> column(SqlTableAlias tableAlias, SqlIdentifier column);

  /**
   * Create new column. No table spec means risking ambiguity if more tables are joined
   *
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return created column
   */
  SqlTableColumn<Object> column(String columnName);

  /**
   * Create new column.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return created column
   */
  SqlTableColumn<Object> column(String tableAlias, String columnName);

  /**
   * Create new column based on given expression.
   *
   * @param expression is expression column should be based on
   * @param <T> is type of expression and subsequently column value
   * @return new column with given expression and alias
   */
  <T> SqlColumn<T> column(Expression<T> expression);

  /**
   * Create column with given name.
   *
   * @param column is name of table column to be assigned to column
   * @param clazz  is type of return value of column
   * @param <T>    is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlTableColumn<T> column(SqlIdentifier column, Class<T> clazz);

  /**
   * Create column with given table alias and name.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlTableColumn<T> column(SqlTableAlias tableAlias, SqlIdentifier column, Class<T> clazz);

  /**
   * Create new column; it is created without table alias, risking ambiguity.
   *
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlTableColumn<T> column(String columnName, Class<T> clazz);

  /**
   * Create new mandatory column; no alias is created, meaning column name will be used instead.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlTableColumn<T> column(String tableAlias, String columnName, Class<T> clazz);

  /**
   * Create column with given SQL text.
   *
   * @param sql is text that will be used as column definition
   * @return created column
   */
  SqlColumn<Object> columnDirect(String sql);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param binds is list of binds used in column
   * @return created column
   */
  SqlColumn<Object> columnDirect(String sql, BindValue<?>... binds);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @return created column
   */
  SqlColumn<Object> columnDirect(String sql, Collection<? extends BindValue<?>> binds);

  /**
   * Create column with given SQL text.
   *
   * @param sql is text that will be used as column definition
   * @param clazz     is type of return value of column
   * @param <T>       is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlColumn<T> columnDirect(String sql, Class<T> clazz);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param clazz is type of return value of column
   * @param binds is list of binds used in column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlColumn<T> columnDirect(String sql, Class<T> clazz, BindValue<?>... binds);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @param clazz is type of return value of column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlColumn<T> columnDirect(String sql, Collection<? extends BindValue<?>> binds,
      Class<T> clazz);

  /**
   * Create column with given SQL text and parse it for bind variables, expressed using :name
   * notation.
   *
   * @param columnSql is text that will be used as column definition
   * @return created column
   */
  SqlColumn columnSql(String columnSql);

  /**
   * Create column with given SQL text and alias and parse it for bind variables, expressed using
   * :name notation.
   *
   * @param sql   is text that will be used as column definition. Binds are parsed from text
   * @param alias is text that will be used as alias for new column
   * @return created column
   */
  SqlColumn columnSql(String sql, String alias);

  /**
   * Add column with given SQL text, alias and parse it for bind variables, expressed using :name
   * notation; bind variables can be supplied to assign value and type to binds.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column
   * @return created column
   */
  SqlColumn columnSql(String sql, String alias, BindValue... binds);

  /**
   * Add column with given SQL text, alias and parse it for bind variables, expressed using :name
   * notation; bind variables can be supplied to assign value and type to binds.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @return created column
   */
  SqlColumn columnSql(String sql, String alias, Collection<? extends BindValue> binds);

  /**
   * Create column with given SQL text and parse it for bind variables, expressed using :name
   * notation.
   *
   * @param columnSql is text that will be used as column definition
   * @param clazz     is type of return value of column
   * @param <T>       is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlColumn<T> columnSql(String columnSql, Class<T> clazz);

  /**
   * Create column with given SQL text and alias and parse it for bind variables, expressed using
   * :name notation.
   *
   * @param sql   is text that will be used as column definition. Binds are parsed from text
   * @param alias is text that will be used as alias for new column
   * @param clazz is type of return value of column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz);

  /**
   * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed
   * using :name notation. Bind variables can be supplied to assign value and type to binds
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param clazz is type of return value of column
   * @param binds is list of binds used in column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz, BindValue... binds);

  /**
   * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed
   * using :name notation. Bind variables can be supplied to assign value and type to binds
   *
   * @param <T>   is Java type corresponding to values in given column
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @param clazz is type of return value of column
   * @return created column
   */
  <T> SqlColumn<T> columnSql(String sql, String alias, Collection<? extends BindValue> binds,
      Class<T> clazz);

  /**
   * Create Sql table alias object based on supplied text. Validates text during creation.
   *
   * @param tableAlias is alias (String value)
   * @return created alias object
   */
  SqlTableAlias tableAlias(String tableAlias);

  /**
   * Create from clause based on table.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return created from clause
   */
  SqlFrom from(SqlIdentifier tableName, SqlTableAlias alias);

  /**
   * Create from clause based on table; String version.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return created from clause
   */
  SqlFrom from(String tableName, String alias);

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return created from clause
   */
  SqlFrom from(Select select, SqlTableAlias alias);

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return created from clause
   */
  SqlFrom from(Select select, String alias);

  /**
   * Create from clause based on Sql expression, directly passed to evaluation without parsing.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  SqlFrom fromDirect(String sqlSelect, SqlTableAlias alias);

  /**
   * Create from clause based on Sql expression, directly pass to evaluation without parsing. Alias
   * as String version
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  SqlFrom fromDirect(String sqlSelect, String alias);

  /**
   * Create from clause based on Sql expression; parses expression to retrieve binds.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  SqlFrom fromSql(String sqlSelect, SqlTableAlias alias);

  /**
   * Create from clause based on Sql expression; parses expression to retrieve binds.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  SqlFrom fromSql(String sqlSelect, String alias);

  /**
   * Create from clause for pseudo-table dual.
   *
   * @return clause from pseudo-table dual
   */
  SqlFrom fromDual();

  /**
   * Create where condition.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @return created where condition
   */
  Condition conditionDirect(String conditionSql);

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  Condition conditionDirect(String conditionSql, BindName... binds);

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  Condition conditionDirect(String conditionSql, Collection<? extends BindName> binds);

  /**
   * Create where condition; parse supplied string to retrieve bind variables.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @return created where condition
   */
  Condition conditionSql(String conditionSql);

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind
   *                     variables. Condition will be surrounded by brackets before adding to
   *                     statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  Condition conditionSql(String conditionSql, BindValue... binds);

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind
   *                     variables. Condition will be surrounded by brackets before adding to
   *                     statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  Condition conditionSql(String conditionSql, Collection<? extends BindValue> binds);

  /**
   * Combine multiple conditions using AND.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  Condition conditionAnd(Condition... whereConditions);

  /**
   * Combine multiple conditions using AND.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  Condition conditionAnd(Collection<Condition> whereConditions);

  /**
   * Create and return joiner, used to combine multiple conditions using AND operator.
   *
   * @return created joiner
   */
  ConditionJoiner conditionAndJoiner();

  /**
   * Combine multiple conditions using OR.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  Condition conditionOr(Condition... whereConditions);

  /**
   * Combine multiple conditions using OR.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  Condition conditionOr(Collection<Condition> whereConditions);

  /**
   * Create and return joiner, used to combine multiple conditions using OR operator.
   *
   * @return created joiner
   */
  ConditionJoiner conditionOrJoiner();

  /**
   * Create equals comparison {@code (first = second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition eq(Expression<T> first, Expression<T> second);

  /**
   * Create not-equal comparison {@code (first != second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition notEq(Expression<T> first, Expression<T> second);

  /**
   * Create less than comparison {@code (first < second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition lessThan(Expression<T> first, Expression<T> second);

  /**
   * Create less or equal comparison {@code (first <= second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition lessOrEqual(Expression<T> first, Expression<T> second);

  /**
   * Create greater than comparison {@code (first > second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition greaterThan(Expression<T> first, Expression<T> second);

  /**
   * Create greater or equal comparison {@code (first >= second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition greaterOrEqual(Expression<T> first, Expression<T> second);

  /**
   * Create is null expression {@code (first IS NULL)}.
   *
   * @param first is parameter of IS NULL expression
   * @param <T>   is type of operand in expression
   * @return created expression (boolean expression / condition)
   */
  <T> Condition isNull(Expression<T> first);

  /**
   * Retrieve SQL NVL function.
   *
   * @param first  is the first parameter
   * @param second is the second parameter
   * @param <T>    is type of the first expression, also used as type of result
   * @return NVL expression
   */
  <T> Expression<T> nvl(Expression<T> first, Expression<? extends T> second);

  /**
   * Retrieve SQL COALESCE function.
   *
   * @param first       is first operand, defining type of coalesce expression
   * @param expressions is list of additional operands
   * @param <T>         is type of first operand and of the result as well
   * @return COALESCE expression
   */
  @SuppressWarnings("unchecked")
  // warns about array of parametrized type, safe in this case
  <T> Expression<T> coalesce(Expression<T> first, Expression<? extends T>... expressions);
}
