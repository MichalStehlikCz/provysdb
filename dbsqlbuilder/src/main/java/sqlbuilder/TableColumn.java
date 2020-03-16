package sqlbuilder;

import com.provys.provysdb.sql.SimpleName;

/**
 * TableColumn is special type of column expression, that is built on single table. It adds option
 * to build new column expression by replacing table alias. Useful when used as static field with
 * default alias, that can be replaced by actual alias on use.
 *
 * @param <T> is Java type this column evaluates to
 */
public interface TableColumn<T> extends SelectExpressionBuilder<T> {

  @Override
  TableColumn<T> as(SimpleName newAlias);

  @Override
  TableColumn<T> as(String newAlias);

  /**
   * Replace column's current table alias with specified one.
   *
   * @param newTableAlias is new alias for table column is based on
   * @return new table column with table alias replaced with specified one
   */
  TableColumn<T> from(QueryAlias newTableAlias);

  /**
   * Replace column's current table alias with specified one.
   *
   * @param newTableAlias is new alias for table column is based on
   * @return new table column with table alias replaced with specified one
   */
  TableColumn<T> from(String newTableAlias);
}
