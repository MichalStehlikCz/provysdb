package sqlbuilder;

import com.provys.provysdb.sql.BindName;
import com.provys.provysdb.sql.SimpleName;
import com.provys.provysdb.sql.Select;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Common ancestor for database contexts, factory class for building statements and their parts.
 * Child interfaces add option to build whole statement, either database connected or disconnected
 */
public interface Sql {

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
  <T> BindValueBuilder<T> bind(BindName bindName, @NonNull T value);

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
  <T> BindValueBuilder<T> bind(String name, @NonNull T value);

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
  <T> BindValueBuilder<T> bind(BindName bindName, @Nullable T value, Class<T> clazz);

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
  <T> BindValueBuilder<T> bind(String name, @Nullable T value, Class<T> clazz);

  /**
   * Create bind value based on supplied type, without value; value is set to null and should be
   * specified later.
   *
   * @param name  is name of bind value, case insensitive
   * @param clazz is type of bind value
   * @param <T>   is type of bind value
   * @return bind value with supplied name and type
   */
  default <T> BindValueBuilder<T> bindEmpty(String name, Class<T> clazz) {
    return bind(name, null, clazz);
  }

  /**
   * Create column with given name.
   *
   * @param column is name of table column to be assigned to column
   * @return created column
   */
  TableColumn<Object> column(SimpleName column);

  /**
   * Create column with given table alias and name.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @return created column
   */
  TableColumn<Object> column(QueryAlias tableAlias, SimpleName column);

  /**
   * Create new column. No table spec means risking ambiguity if more tables are joined
   *
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return created column
   */
  TableColumn<Object> column(String columnName);

  /**
   * Create new column.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return created column
   */
  TableColumn<Object> column(String tableAlias, String columnName);

  /**
   * Create new column based on given expression.
   *
   * @param expression is expression column should be based on
   * @param <T> is type of expression and subsequently column value
   * @return new column with given expression and alias
   */
  <T> SqlColumn<T> column(SelectExpressionBuilder<T> expression);

  /**
   * Create column with given name.
   *
   * @param column is name of table column to be assigned to column
   * @param clazz  is type of return value of column
   * @param <T>    is Java type corresponding to values in given column
   * @return created column
   */
  <T> TableColumn<T> column(SimpleName column, Class<T> clazz);

  /**
   * Create column with given table alias and name.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  <T> TableColumn<T> column(QueryAlias tableAlias, SimpleName column, Class<T> clazz);

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
  <T> TableColumn<T> column(String columnName, Class<T> clazz);

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
  <T> TableColumn<T> column(String tableAlias, String columnName, Class<T> clazz);

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
  SqlColumn<Object> columnDirect(String sql, BindValueBuilder<?>... binds);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @return created column
   */
  SqlColumn<Object> columnDirect(String sql, Collection<? extends BindValueBuilder<?>> binds);

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
  <T> SqlColumn<T> columnDirect(String sql, Class<T> clazz, BindValueBuilder<?>... binds);

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
  <T> SqlColumn<T> columnDirect(String sql, Collection<? extends BindValueBuilder<?>> binds,
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
  SqlColumn columnSql(String sql, String alias, BindValueBuilder... binds);

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
  SqlColumn columnSql(String sql, String alias, Collection<? extends BindValueBuilder> binds);

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
  <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz, BindValueBuilder... binds);

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
  <T> SqlColumn<T> columnSql(String sql, String alias, Collection<? extends BindValueBuilder> binds,
      Class<T> clazz);

  /**
   * Create Sql table alias object based on supplied text. Validates text during creation.
   *
   * @param tableAlias is alias (String value)
   * @return created alias object
   */
  QueryAlias tableAlias(String tableAlias);

  /**
   * Create from clause based on table.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return created from clause
   */
  FromClause from(SimpleName tableName, QueryAlias alias);

  /**
   * Create from clause based on table; String version.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return created from clause
   */
  FromClause from(String tableName, String alias);

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return created from clause
   */
  FromClause from(Select select, QueryAlias alias);

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return created from clause
   */
  FromClause from(Select select, String alias);

  /**
   * Create from clause based on Sql expression, directly passed to evaluation without parsing.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  FromClause fromDirect(String sqlSelect, QueryAlias alias);

  /**
   * Create from clause based on Sql expression, directly pass to evaluation without parsing. Alias
   * as String version
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  FromClause fromDirect(String sqlSelect, String alias);

  /**
   * Create from clause based on Sql expression; parses expression to retrieve binds.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  FromClause fromSql(String sqlSelect, QueryAlias alias);

  /**
   * Create from clause based on Sql expression; parses expression to retrieve binds.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  FromClause fromSql(String sqlSelect, String alias);

  /**
   * Create from clause for pseudo-table dual.
   *
   * @return clause from pseudo-table dual
   */
  FromClause fromDual();

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
  Condition conditionSql(String conditionSql, BindValueBuilder... binds);

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind
   *                     variables. Condition will be surrounded by brackets before adding to
   *                     statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  Condition conditionSql(String conditionSql, Collection<? extends BindValueBuilder> binds);

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
  <T> Condition eq(SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second);

  /**
   * Create not-equal comparison {@code (first != second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition notEq(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second);

  /**
   * Create less than comparison {@code (first < second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition lessThan(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second);

  /**
   * Create less or equal comparison {@code (first <= second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition lessOrEqual(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second);

  /**
   * Create greater than comparison {@code (first > second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition greaterThan(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second);

  /**
   * Create greater or equal comparison {@code (first >= second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  <T> Condition greaterOrEqual(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second);

  /**
   * Create is null expression {@code (first IS NULL)}.
   *
   * @param first is parameter of IS NULL expression
   * @param <T>   is type of operand in expression
   * @return created expression (boolean expression / condition)
   */
  <T> Condition isNull(SelectExpressionBuilder<T> first);

  /**
   * Retrieve SQL NVL function.
   *
   * @param first  is the first parameter
   * @param second is the second parameter
   * @param <T>    is type of the first expression, also used as type of result
   * @return NVL expression
   */
  <T> SelectExpressionBuilder<T> nvl(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<? extends T> second);

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
  <T> SelectExpressionBuilder<T> coalesce(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<? extends T>... expressions);
}
