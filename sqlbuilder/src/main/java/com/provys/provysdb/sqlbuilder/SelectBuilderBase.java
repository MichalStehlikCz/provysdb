package com.provys.provysdb.sqlbuilder;

import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Internal interface, used to build published select builder interfaces. Should generally not be
 * used directly.
 *
 * @param <T> is actual interface / class extending select builder (used to allow return of self
 *            references)
 * @param <U> is corresponding interface, used when columns or their type information is missing
 *            from type signature
 */
public interface SelectBuilderBase<T extends SelectBuilderBase<T, U>,
    U extends SelectBuilderBase<U, U>> {

  /**
   * List defined columns.
   *
   * @return list of already defined columns
   */
  List<SqlColumn> getColumns();

  /**
   * Add column to list of columns. It is expected to come from last item, added to from clause. If
   * no items were added to from clause, column is added as is, without table alias
   *
   * @param column is name of table column to be assigned to column
   * @return self to support fluent build
   */
  U column(SqlIdentifier column);

  /**
   * Add column with given name and alias.
   *
   * @param column is name of table column to be assigned to column
   * @param alias  is alias to be sued for column
   * @return self to support fluent build
   */
  U column(SqlIdentifier column, SqlIdentifier alias);

  /**
   * Add column with given table alias, name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param alias      is alias to be sued for column
   * @return self to support fluent build
   */
  U column(SqlTableAlias tableAlias, SqlIdentifier column, SqlIdentifier alias);

  /**
   * Add column to list of columns. It is expected to come from last item, added to from clause. If
   * no items were added to from clause, column is added as is, without table alias
   *
   * @param columnName is name of column; it should be column in last item, added to from clause. It
   *                   must be valid column name (in "" or first character letter and remaining
   *                   letters, numbers and characters $ and #). Use columnSql to add columns based
   *                   on sql expressions
   * @return self to support fluent build
   */
  U column(String columnName);

  /**
   * Add column with table alias, column name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param alias      is alias to be used for column
   * @return self to support fluent build
   */
  U column(String tableAlias, String columnName, String alias);

  /**
   * Add new column; no alias is created, meaning column name will be used instead.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @return self to support fluent build
   */
  U column(String tableAlias, String columnName);

  /**
   * Add column based on given expression.
   *
   * @param expression is expression column should be based on
   * @param alias      is alias used for column
   * @return self to support fluent build
   */
  U column(Expression expression, SqlIdentifier alias);

  /**
   * Add column based on given expression.
   *
   * @param expression is expression column should be based on
   * @param alias      is alias used for column
   * @return self to support fluent build
   */
  U column(Expression expression, String alias);

  /**
   * Add column with given SQL text.
   *
   * @param columnSql is text that will be used as column definition
   * @return self to support fluent build
   */
  U columnDirect(String columnSql);

  /**
   * Add column with given SQL text and alias.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @return self to support fluent build
   */
  U columnDirect(String columnSql, String alias);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param binds     is list of binds, associated with given column
   * @return self to support fluent build
   */
  U columnDirect(String columnSql, String alias, BindName... binds);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param binds     is list of binds, associated with given column
   * @return self to support fluent build
   */
  U columnDirect(String columnSql, String alias, Collection<? extends BindName> binds);

  /**
   * Add column with given SQL text, parse text for binds.
   *
   * @param columnSql is text that will be used as column definition
   * @return self to support fluent build
   */
  U columnSql(String columnSql);

  /**
   * Add column with given SQL text and alias, parse text for binds.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @return self to support fluent build
   */
  U columnSql(String columnSql, String alias);

  /**
   * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to
   * specify types and values
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param binds     is list of binds, associated with given column
   * @return self to support fluent build
   */
  U columnSql(String columnSql, String alias, BindValue... binds);

  /**
   * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to
   * specify types and values
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param binds     is list of binds, associated with given column
   * @return self to support fluent build
   */
  U columnSql(String columnSql, String alias, Collection<? extends BindValue> binds);

  /**
   * Add table to from clause of the statement.
   *
   * @param table is table definition (potentially with join condition)
   * @return self to support fluent build
   */
  T from(SqlFrom table);

  /**
   * Create from clause based on table.
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return self to support fluent build
   */
  T from(SqlIdentifier tableName, SqlTableAlias alias);

  /**
   * Create from clause based on table. String version
   *
   * @param tableName is name of table select is from
   * @param alias     is alias new table will get
   * @return self to support fluent build
   */
  T from(String tableName, String alias);

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return self to support fluent build
   */
  T from(Select select, SqlTableAlias alias);

  /**
   * Add sql expression to from clause of the statement.
   *
   * @param select is select statement that will be used in from clause
   * @param alias  as alias to be assigned to given expression
   * @return self to support fluent build
   */
  T from(Select select, String alias);

  /**
   * Create from clause based on Sql expression.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return self to support fluent build
   */
  T fromDirect(String sqlSelect, SqlTableAlias alias);

  /**
   * Create from clause based on Sql expression. String version
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return self to support fluent build
   */
  T fromDirect(String sqlSelect, String alias);

  /**
   * Create from clause based on Sql expression.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return self to support fluent build
   */
  T fromSql(String sqlSelect, SqlTableAlias alias);

  /**
   * Create from clause based on Sql expression; String version.
   *
   * @param sqlSelect is SQL expression used as data source in SQL clause
   * @param alias     is alias new table will get
   * @return self to support fluent build
   */
  T fromSql(String sqlSelect, String alias);

  /**
   * Add from clause for pseudo-table dual.
   *
   * @return self to support fluent build
   */
  T fromDual();

  /**
   * Add where condition.
   *
   * @param where is sql where condition to be added (or null, in tht case nothing is added)
   * @return self to support fluent build
   */
  T where(@Nullable Condition where);

  /**
   * Add where condition.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @return self to support fluent build
   */
  T whereDirect(String conditionSql);

  /**
   * Add where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return self to support fluent build
   */
  T whereDirect(String conditionSql, BindName... binds);

  /**
   * Add where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return self to support fluent build
   */
  T whereDirect(String conditionSql, Collection<? extends BindName> binds);

  /**
   * Add where condition.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @return self to support fluent build
   */
  T whereSql(String conditionSql);

  /**
   * Add where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return self to support fluent build
   */
  T whereSql(String conditionSql, BindValue... binds);

  /**
   * Add where condition with binds.
   *
   * @param conditionSql is text of condition to be added. Condition will be surrounded by brackets
   *                     before adding to statement
   * @param binds        is list of bind variables, associated with condition
   * @return self to support fluent build
   */
  T whereSql(String conditionSql, Collection<? extends BindValue> binds);

  /**
   * Add multiple conditions combined using AND.
   *
   * @param whereConditions are where conditions to be combined
   * @return self to support fluent build
   */
  T whereAnd(Condition... whereConditions);

  /**
   * Add multiple conditions combined using AND.
   *
   * @param whereConditions are where conditions to be combined
   * @return self to support fluent build
   */
  T whereAnd(Collection<Condition> whereConditions);

  /**
   * Add multiple conditions combined using OR.
   *
   * @param whereConditions are where conditions to be combined
   * @return self to support fluent build
   */
  T whereOr(Condition... whereConditions);

  /**
   * Add multiple conditions combined using OR.
   *
   * @param whereConditions are where conditions to be combined
   * @return self to support fluent build
   */
  T whereOr(Collection<Condition> whereConditions);

  /**
   * Build select from builder.
   *
   * @return resulting (non-mutable) select statement
   */
  Select build();

  /**
   * Create clone of the statement. Does deep copy, but keeps references to non-mutable objects
   *
   * @return clone of this statement
   */
  T copy();
}
