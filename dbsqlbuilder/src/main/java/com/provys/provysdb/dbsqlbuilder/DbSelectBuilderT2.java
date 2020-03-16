package com.provys.provysdb.dbsqlbuilder;

import com.provys.provysdb.sql.BindName;
import sqlbuilder.BindValueBuilder;
import sqlbuilder.SelectBuilderBase;
import com.provys.provysdb.builder.sqlbuilder.SqlColumn;
import com.provys.provysdb.sql.SimpleName;
import sqlbuilder.QueryAlias;
import java.util.Collection;

/**
 * Database backed select interface with two columns.
 *
 * @param <T1> is type of the first column
 * @param <T2> is type of the second column
 */
public interface DbSelectBuilderT2<T1, T2>
    extends SelectBuilderBase<DbSelectBuilderT2<T1, T2>, DbSelectBuilder>, DbSelectBuilderBase {

  /**
   * Add column to list of columns.
   *
   * @param column is definition of the column to be added
   * @param <T>    is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(SqlColumn<T> column);

  /**
   * Add column to list of columns. It is expected to come from last item, added to from clause. If
   * no items were added to from clause, column is added as is, without table alias
   *
   * @param column is name of table column to be assigned to column
   * @param clazz  is type of return value of column
   * @param <T>    is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(SimpleName column, Class<T> clazz);

  /**
   * Add column with given name and alias.
   *
   * @param column is name of table column to be assigned to column
   * @param alias  is alias to be sued for column
   * @param clazz  is type of return value of column
   * @param <T>    is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(SimpleName column, SimpleName alias, Class<T> clazz);

  /**
   * Add column with given table alias, name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param column     is name of table column to be assigned to column
   * @param alias      is alias to be sued for column
   * @param clazz      is type of return value of column
   * @param <T>        is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(QueryAlias tableAlias, SimpleName column, SimpleName alias,
      Class<T> clazz);

  /**
   * Add column to list of columns. It is expected to come from last item, added to from clause. If
   * no items were added to from clause, column is added as is, without table alias
   *
   * @param columnName is name of column; it should be column in last item, added to from clause. It
   *                   must be valid column name (in "" or first character letter and remaining
   *                   letters, numbers and characters $ and #). Use columnSql to add columns based
   *                   on sql expressions
   * @param clazz      is type of return value of column
   * @param <T>        is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(String columnName, Class<T> clazz);

  /**
   * Add new column. No alias is created, meaning column name will be used instead
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param clazz      is type of return value of column
   * @param <T>        is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(String tableAlias, String columnName, Class<T> clazz);

  /**
   * Add column with table alias, column name and alias.
   *
   * @param tableAlias is alias of table column is in
   * @param columnName is name of column. It must be valid column name (in "" or first character
   *                   letter and remaining letters, numbers and characters $ and #). Use columnSql
   *                   to add columns based on sql expressions
   * @param alias      is alias to be used for column
   * @param clazz      is type of return value of column
   * @param <T>        is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder column(String tableAlias, String columnName, String alias, Class<T> clazz);

  /**
   * Add column with given SQL text.
   *
   * @param columnSql is text that will be used as column definition
   * @param clazz     is type of return value of column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnDirect(String columnSql, Class<T> clazz);

  /**
   * Add column with given SQL text and alias.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param clazz     is type of return value of column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnDirect(String columnSql, String alias, Class<T> clazz);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param clazz     is type of return value of column
   * @param binds     is list of binds, associated with given column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnDirect(String columnSql, String alias, Class<T> clazz,
      BindName... binds);

  /**
   * Add column with given SQL text, alias and binds to list of columns.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param binds     is list of binds, associated with given column
   * @param clazz     is type of return value of column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnDirect(String columnSql, String alias,
      Collection<? extends BindName> binds, Class<T> clazz);

  /**
   * Add column with given SQL text, parse text for binds.
   *
   * @param columnSql is text that will be used as column definition
   * @param clazz     is type of return value of column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnSql(String columnSql, Class<T> clazz);

  /**
   * Add column with given SQL text and alias, parse text for binds.
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param clazz     is type of return value of column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnSql(String columnSql, String alias, Class<T> clazz);

  /**
   * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to
   * specify types and values
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param clazz     is type of return value of column
   * @param binds     is list of binds, associated with given column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnSql(String columnSql, String alias, Class<T> clazz, BindValueBuilder... binds);

  /**
   * Add column with given SQL text and alias. Parse text for binds; use supplied bind variables to
   * specify types and values
   *
   * @param columnSql is text that will be used as column definition
   * @param alias     is text that will be used as alias for new column
   * @param binds     is list of binds, associated with given column
   * @param clazz     is type of return value of column
   * @param <T>       is type of column being added
   * @return resulting select builder
   */
  <T> DbSelectBuilder columnSql(String columnSql, String alias,
      Collection<? extends BindValueBuilder> binds, Class<T> clazz);
}
