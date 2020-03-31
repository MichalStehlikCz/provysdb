package sqlbuilder;

import com.provys.provysdb.sql.CodeBuilder;

/**
 * Can represent column in a select query.
 * Based on Sql syntax, it is generally either expression with optional alias or * clause (strongly
 * discouraged to be used in StyleGuide)
 *
 * @param <T> is type of value of the column
 */
public interface SelectColumn<T> extends SelectColumnInfo {

  /**
   * Java type, corresponding to this expression's type.
   *
   * @return Java type this column should be mapped to. Used to find proper adapter for value
   *     retrieval
   */
  @Override
  Class<T> getType();

  /**
   * Appends column com.provys.db.sql to code builder, used to construct statement. Unlike addColumnSql, does
   * not append column alias. Also adds associated binds. Used to generate column list of statement.
   *
   * @param builder is CodeBuilder, used to construct com.provys.db.sql text
   */
  void addColumn(CodeBuilder builder);
}
