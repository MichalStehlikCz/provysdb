package com.provys.provysdb.sqlbuilder;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtUid;
import com.provys.provysdb.sqlbuilder.impl.NoDbSqlImpl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Factory class, used to produce instances of basic objects (names, identifiers, binds etc.).
 * Recommended to be used via static import or to construct static objects (like table or column
 * accessors). More complex objects (e.g. ones that require parser or use additional
 * parametrisation) are produced with default parser / parametrisation and without database access
 * via this static factory. It is necessary to use instance of {@code Sql} interface implementation
 * that allows proper settings in such situations.
 */
public final class SqlFactory {

  /**
   * Static methods use this dynamically created instance to produce objects in static methods. This
   * enables
   */
  private static final NoDbSql SQL = new NoDbSqlImpl();

  /**
   * Select statement builder.
   *
   * @return select statement builder
   */
  public static SelectBuilderT0 select() {
    return SQL.select();
  }

  /**
   * String literal.
   *
   * @param value is value of literal
   * @return varchar literal
   */
  public static Literal<String> literal(String value) {
    return SQL.literal(value);
  }

  /**
   * Byte literal.
   *
   * @param value is value of literal
   * @return byte literal
   */
  public static Literal<Byte> literal(byte value) {
    return SQL.literal(value);
  }

  /**
   * Short literal.
   *
   * @param value is value of literal
   * @return short literal
   */
  public static Literal<Short> literal(short value) {
    return SQL.literal(value);
  }

  /**
   * Integer literal.
   *
   * @param value is value of literal
   * @return integer literal
   */
  public static Literal<Integer> literal(int value) {
    return SQL.literal(value);
  }

  /**
   * Long literal.
   *
   * @param value is value of literal
   * @return long literal
   */
  public static Literal<Long> literal(long value) {
    return SQL.literal(value);
  }

  /**
   * BigInteger literal.
   *
   * @param value is value of literal
   * @return BigInteger literal
   */
  public static Literal<BigInteger> literal(BigInteger value) {
    return SQL.literal(value);
  }

  /**
   * Float literal.
   *
   * @param value is value of literal
   * @return float literal
   */
  public static Literal<Float> literal(float value) {
    return SQL.literal(value);
  }

  /**
   * Double literal.
   *
   * @param value is value of literal
   * @return double literal
   */
  public static Literal<Double> literal(double value) {
    return SQL.literal(value);
  }

  /**
   * BigDecimal literal.
   *
   * @param value is value of literal
   * @return BigDecimal literal
   */
  public static Literal<BigDecimal> literal(BigDecimal value) {
    return SQL.literal(value);
  }

  /**
   * DtUid literal.
   *
   * @param value is value of literal
   * @return DtUid literal
   */
  public static Literal<DtUid> literal(DtUid value) {
    return SQL.literal(value);
  }

  /**
   * DtDate literal.
   *
   * @param value is value of literal
   * @return date literal
   */
  public static Literal<DtDate> literal(DtDate value) {
    return SQL.literal(value);
  }

  /**
   * DtDateTime literal.
   *
   * @param value is value of literal
   * @return datetime literal
   */
  public static Literal<DtDateTime> literal(DtDateTime value) {
    return SQL.literal(value);
  }

  /**
   * String literal represented as NVARCHAR2 in database.
   *
   * @param value is value of literal
   * @return NVarchar literal
   */
  public static Literal<String> literalNVarchar(String value) {
    return SQL.literalNVarchar(value);
  }

  /**
   * Create bind name based on supplied String.
   *
   * @param name is name of bind value, case insensitive
   * @return bind name
   */
  public static BindName bind(String name) {
    return SQL.bind(name);
  }

  /**
   * Create bind value based on supplied (non-null) value. Note that bind will take type from
   * supplied value. This is convenient if you control type of supplied value or you do not want to
   * bind different value to the same statement in future, but in many situations, you might want to
   * consider variant with explicit specification of bind type
   *
   * @param bindName is name bind value will be associated with
   * @param value    is value bind will get assigned; it is also used to infer type
   * @param <T>      is type of bind value
   * @return bind value with supplied name and value
   */
  public static <T> BindValue<T> bind(BindName bindName, @NonNull T value) {
    return SQL.bind(bindName, value);
  }

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
  public static <T> BindValue<T> bind(String name, @NonNull T value) {
    return SQL.bind(name, value);
  }

  /**
   * Create bind value of supplied type based on supplied value; value might be null.
   *
   * @param bindName is name bind value will be associated with
   * @param value    is value bind will get assigned
   * @param clazz    defines type of bind value
   * @param <T>      is type of bind value
   * @return bind value of given type with supplied name and value
   */
  public static <T> BindValue<T> bind(BindName bindName, @Nullable T value, Class<T> clazz) {
    return SQL.bind(bindName, value, clazz);
  }

  /**
   * Create bind value of supplied type based on supplied value; value might be null.
   *
   * @param name  is name of bind value, case insensitive
   * @param value is value bind will get assigned
   * @param clazz defines type of bind value
   * @param <T>   is type of bind value
   * @return bind value of given type with supplied name and value
   */
  public static <T> BindValue<T> bind(String name, @Nullable T value, Class<T> clazz) {
    return SQL.bind(name, value, clazz);
  }

  /**
   * Create bind value based on supplied type, without value. Value is set to null and should be
   * specified later.
   *
   * @param name  is name of bind value, case insensitive
   * @param clazz is type of bind value
   * @param <T>   is type of bind value
   * @return bind value with supplied name and type
   */
  public static <T> BindValue<T> bindEmpty(String name, Class<T> clazz) {
    return SQL.bindEmpty(name, clazz);
  }

  /**
   * Create Sql name object based on supplied text. Validates name during creation.
   *
   * @param name to be created
   * @return created name object
   */
  public static Identifier name(String name) {
    return SQL.name(name);
  }

  /**
   * Create column with given name.
   *
   * @param column is name of table column to be assigned to column
   * @return created column
   */
  public static TableColumn column(Identifier column) {
    return SQL.column(column);
  }

  /**
   * Create column with given name and alias.
   *
   * @param column is name of table column to be assigned to column
   * @param alias  is alias to be sued for column
   * @return created column
   */
  public static TableColumn column(Identifier column, Identifier alias) {
    return SQL.column(column, alias);
  }

  /**
   * Create column with given table alias, name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @return created column
   */
  public static TableColumn column(QueryAlias tableAlias, Identifier column) {
    return SQL.column(tableAlias, column);
  }

  /**
   * Create column with given table alias, name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param alias      is alias to be sued for column
   * @return created column
   */
  public static TableColumn column(QueryAlias tableAlias, Identifier column,
      Identifier alias) {
    return SQL.column(tableAlias, column, alias);
  }

  /**
   * Create new column. No alias is created, meaning column name will be used instead and no table
   * spec risking ambiguity if more tables are joined
   *
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return created column
   */
  public static TableColumn column(String columnName) {
    return SQL.column(columnName);
  }

  /**
   * Create new column. No alias is created, meaning column name will be used instead
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return created column
   */
  public static TableColumn column(String tableAlias, String columnName) {
    return SQL.column(tableAlias, columnName);
  }

  /**
   * Create new column.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param alias      is alias to be used for column
   * @return created column
   */
  public static TableColumn column(String tableAlias, String columnName, String alias) {
    return SQL.column(tableAlias, columnName, alias);
  }

  /**
   * Create typed column based on supplied (untyped) column and type.
   *
   * @param column is untyped column
   * @param clazz  is type of return value of column
   * @param <T>    is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> column(SqlColumn column, Class<T> clazz) {
    return SQL.column(column, clazz);
  }

  /**
   * Create typed table column based on supplied (untyped) table column and type.
   *
   * @param column is untyped column
   * @param clazz  is type of return value of column
   * @param <T>    is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(TableColumn column, Class<T> clazz) {
    return SQL.column(column, clazz);
  }

  /**
   * Create mandatory column with given name.
   *
   * @param column is name of table column to be assigned to column
   * @param clazz  is type of return value of column
   * @param <T>    is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(Identifier column, Class<T> clazz) {
    return SQL.column(column, clazz);
  }

  /**
   * Create mandatory column with given name and alias.
   *
   * @param column is name of table column to be assigned to column
   * @param alias  is alias to be sued for column
   * @param clazz  is type of return value of column
   * @param <T>    is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(Identifier column, Identifier alias,
      Class<T> clazz) {
    return SQL.column(column, alias, clazz);
  }

  /**
   * Create mandatory column with given table alias, name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(QueryAlias tableAlias, Identifier column,
      Class<T> clazz) {
    return SQL.column(tableAlias, column, clazz);
  }

  /**
   * Create mandatory column with given table alias, name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param alias      is alias to be sued for column
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(QueryAlias tableAlias, Identifier column,
      Identifier alias, Class<T> clazz) {
    return SQL.column(tableAlias, column, alias, clazz);
  }

  /**
   * Create new mandatory column. It is created without table alias, risking ambiguity
   *
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(String columnName, Class<T> clazz) {
    return SQL.column(columnName, clazz);
  }

  /**
   * Create new mandatory column. No alias is created, meaning column name will be sued instead
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(String tableAlias, String columnName,
      Class<T> clazz) {
    return SQL.column(tableAlias, columnName, clazz);
  }

  /**
   * Create new mandatory column.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param alias      is alias to be used for column
   * @param clazz      is type of return value of column
   * @param <T>        is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> TableColumn<T> column(String tableAlias, String columnName, String alias,
      Class<T> clazz) {
    return SQL.column(tableAlias, columnName, alias, clazz);
  }

  /**
   * Create column with given SQL text.
   *
   * @param columnSql is text that will be used as column definition
   * @return created column
   */
  public static SqlColumn columnDirect(String columnSql) {
    return SQL.columnDirect(columnSql);
  }

  /**
   * Create column with given SQL text and alias.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @return created column
   */
  public static SqlColumn columnDirect(String sql, String alias) {
    return SQL.columnDirect(sql, alias);
  }

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column
   * @return created column
   */
  public static SqlColumn columnDirect(String sql, String alias, BindName... binds) {
    return SQL.columnDirect(sql, alias, binds);
  }

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @return created column
   */
  public static SqlColumn columnDirect(String sql, String alias, List<BindName> binds) {
    return SQL.columnDirect(sql, alias, binds);
  }

  /**
   * Create mandatory column with given SQL text.
   *
   * @param columnSql is text that will be used as column definition
   * @param clazz     is type of return value of column
   * @param <T>       is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnDirect(String columnSql, Class<T> clazz) {
    return SQL.columnDirect(columnSql, clazz);
  }

  /**
   * Create mandatory column with given SQL text and alias.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param clazz is type of return value of column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnDirect(String sql, String alias, Class<T> clazz) {
    return SQL.columnDirect(sql, alias, clazz);
  }

  /**
   * Add mandatory column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param clazz is type of return value of column
   * @param binds is list of binds used in column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnDirect(String sql, String alias, Class<T> clazz,
      BindName... binds) {
    return SQL.columnDirect(sql, alias, clazz, binds);
  }

  /**
   * Add mandatory column with given SQL text, alias and binds to list of columns.
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @param clazz is type of return value of column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnDirect(String sql, String alias, List<BindName> binds,
      Class<T> clazz) {
    return SQL.columnDirect(sql, alias, binds, clazz);
  }

  /**
   * Create column with given SQL text and parse it for bind variables, expressed using :name
   * notation.
   *
   * @param columnSql is text that will be used as column definition
   * @return created column
   */
  public static SqlColumn columnSql(String columnSql) {
    return SQL.columnSql(columnSql);
  }

  /**
   * Create column with given SQL text and alias and parse it for bind variables, expressed using
   * :name notation.
   *
   * @param sql   is text that will be used as column definition. Binds are parsed from text
   * @param alias is text that will be used as alias for new column
   * @return created column
   */
  public static SqlColumn columnSql(String sql, String alias) {
    return SQL.columnSql(sql, alias);
  }

  /**
   * Add column with given SQL text, alias and parse it for bind variables, expressed using :name
   * notation. Bind variables can be supplied to assign value and type to binds
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column
   * @return created column
   */
  public static SqlColumn columnSql(String sql, String alias, BindValue... binds) {
    return SQL.columnSql(sql, alias, binds);
  }

  /**
   * Add column with given SQL text, alias and parse it for bind variables, expressed using :name
   * notation. Bind variables can be supplied to assign value and type to binds
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @return created column
   */
  public static SqlColumn columnSql(String sql, String alias,
      Collection<? extends BindValue> binds) {
    return SQL.columnSql(sql, alias, binds);
  }

  /**
   * Create mandatory column with given SQL text and parse it for bind variables, expressed using
   * :name notation.
   *
   * @param columnSql is text that will be used as column definition
   * @param clazz     is type of return value of column
   * @param <T>       is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnSql(String columnSql, Class<T> clazz) {
    return SQL.columnSql(columnSql, clazz);
  }

  /**
   * Create mandatory column with given SQL text and alias and parse it for bind variables,
   * expressed using :name notation.
   *
   * @param sql   is text that will be used as column definition. Binds are parsed from text
   * @param alias is text that will be used as alias for new column
   * @param clazz is type of return value of column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz) {
    return SQL.columnSql(sql, alias, clazz);
  }

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
  public static <T> SqlColumn<T> columnSql(String sql, String alias, Class<T> clazz,
      BindValue... binds) {
    return SQL.columnSql(sql, alias, clazz, binds);
  }

  /**
   * Add mandatory column with given SQL text, alias and parse it for bind variables, expressed
   * using :name notation. Bind variables can be supplied to assign value and type to binds
   *
   * @param sql   is text that will be used as column definition
   * @param alias is text that will be used as alias for new column
   * @param binds is list of binds used in column, in proper oder, binds should be referenced using
   *              Java conventions (e.g. using ? as placeholder)
   * @param clazz is type of return value of column
   * @param <T>   is Java type corresponding to values in given column
   * @return created column
   */
  public static <T> SqlColumn<T> columnSql(String sql, String alias, Collection<BindValue> binds,
      Class<T> clazz) {
    return SQL.columnSql(sql, alias, binds, clazz);
  }

  /**
   * Create Sql table alias object based on supplied text. Validates text during creation.
   *
   * @param tableAlias is alias (String value)
   * @return created alias object
   */
  public static QueryAlias tableAlias(String tableAlias) {
    return SQL.tableAlias(tableAlias);
  }

  /**
   * Create from clause based on table.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return created from clause
   */
  public static FromClause from(Identifier tableName, QueryAlias alias) {
    return SQL.from(tableName, alias);
  }

  /**
   * Create from clause based on table; String version.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return created from clause
   */
  public static FromClause from(String tableName, String alias) {
    return SQL.from(tableName, alias);
  }

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return created from clause
   */
  public static FromClause from(Select select, QueryAlias alias) {
    return SQL.from(select, alias);
  }

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return created from clause
   */
  public static FromClause from(Select select, String alias) {
    return SQL.from(select, alias);
  }

  /**
   * Create from clause based on Sql expression, directly passed to evaluation without parsing.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  public static FromClause fromDirect(String sqlSelect, QueryAlias alias) {
    return SQL.fromDirect(sqlSelect, alias);
  }

  /**
   * Create from clause based on Sql expression, directly pass to evaluation without parsing. Alias
   * as String version
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  public static FromClause fromDirect(String sqlSelect, String alias) {
    return SQL.fromDirect(sqlSelect, alias);
  }

  /**
   * Create from clause based on Sql expression; parse expression to retrieve binds.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  public static FromClause fromSql(String sqlSelect, QueryAlias alias) {
    return SQL.fromSql(sqlSelect, alias);
  }

  /**
   * Create from clause based on Sql expression; parse expression to retrieve binds.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return created from clause
   */
  public static FromClause fromSql(String sqlSelect, String alias) {
    return SQL.fromSql(sqlSelect, alias);
  }

  /**
   * Create from clause for pseudo-table dual.
   *
   * @return clause fro pseudo-table dual
   */
  public static FromClause fromDual() {
    return SQL.fromDual();
  }

  /**
   * Create where condition.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @return created where condition
   */
  public static Condition conditionDirect(String conditionSql) {
    return SQL.conditionDirect(conditionSql);
  }

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  public static Condition conditionDirect(String conditionSql, BindName... binds) {
    return SQL.conditionDirect(conditionSql, binds);
  }

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  public static Condition conditionDirect(String conditionSql, List<BindName> binds) {
    return SQL.conditionDirect(conditionSql, binds);
  }

  /**
   * Create where condition; parse supplied string to retrieve bind variables.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @return created where condition
   */
  public static Condition conditionSql(String conditionSql) {
    return SQL.conditionSql(conditionSql);
  }

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind
   *                     variables. Condition will be surrounded by brackets before adding to
   *                     statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  public static Condition conditionSql(String conditionSql, BindValue... binds) {
    return SQL.conditionSql(conditionSql, binds);
  }

  /**
   * Create where condition with binds.
   *
   * @param conditionSql is text of condition to be added; parse supplied string to retrieve bind
   *                     variables. Condition will be surrounded by brackets before adding to
   *                     statement
   * @param binds        is list of bind variables, associated with condition
   * @return created where condition
   */
  public static Condition conditionSql(String conditionSql, Collection<BindValue> binds) {
    return SQL.conditionSql(conditionSql, binds);
  }

  /**
   * Combine multiple conditions using AND.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  public static Condition conditionAnd(Condition... whereConditions) {
    return SQL.conditionAnd(whereConditions);
  }

  /**
   * Combine multiple conditions using AND.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  public static Condition conditionAnd(Collection<Condition> whereConditions) {
    return SQL.conditionAnd(whereConditions);
  }

  /**
   * Create and return joiner, used to combine multiple conditions using AND operator.
   *
   * @return created joiner
   */
  public static ConditionJoiner conditionAndJoiner() {
    return SQL.conditionAndJoiner();
  }

  /**
   * Combine multiple conditions using OR.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  public static Condition conditionOr(Condition... whereConditions) {
    return SQL.conditionOr(whereConditions);
  }

  /**
   * Combine multiple conditions using OR.
   *
   * @param whereConditions are where conditions to be combined
   * @return created where condition
   */
  public static Condition conditionOr(Collection<Condition> whereConditions) {
    return SQL.conditionOr(whereConditions);
  }

  /**
   * Create and return joiner, used to combine multiple conditions using OR operator.
   *
   * @return created joiner
   */
  public static ConditionJoiner conditionOrJoiner() {
    return SQL.conditionOrJoiner();
  }

  /**
   * Create equals comparison {@code (first = second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  public static <T> Condition eq(SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return SQL.eq(first, second);
  }

  /**
   * Create not-equal comparison {@code (first != second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  public static <T> Condition notEq(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return SQL.notEq(first, second);
  }

  /**
   * Create less than comparison {@code (first < second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  public static <T> Condition lessThan(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return SQL.lessThan(first, second);
  }

  /**
   * Create less or equal comparison {@code (first <= second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  public static <T> Condition lessOrEqual(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return SQL.lessOrEqual(first, second);
  }

  /**
   * Create greater than comparison {@code (first > second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  public static <T> Condition greaterThan(
      SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return SQL.greaterThan(first, second);
  }

  /**
   * Create greater or equal comparison {@code (first >= second)}.
   *
   * @param first  is first operand
   * @param second is second operand in comparison
   * @param <T>    is type of operands in comparison
   * @return created comparison (boolean expression / condition)
   */
  public static <T> Condition greaterOrEqual(SelectExpressionBuilder<T> first, SelectExpressionBuilder<T> second) {
    return SQL.greaterOrEqual(first, second);
  }

  /**
   * Create is null expression {@code (first IS NULL)}.
   *
   * @param first is parameter of IS NULL expression
   * @param <T>   is type of operand
   * @return created expression (boolean expression / condition)
   */
  public static <T> Condition isNull(SelectExpressionBuilder<T> first) {
    return SQL.isNull(first);
  }

  /**
   * Pure utility class, cannot be instantiated.
   */
  private SqlFactory() {
  }
}