package sqlbuilder;

import com.provys.provysdb.sql.CodeBuilder;
import com.provys.provysdb.sql.SimpleName;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Interface represents element of from clause - e.g. either just table to be selected from or join
 * clause.
 */
public interface FromClause {

  /**
   * Alias associated with given expression.
   *
   * @return alias this table is associated with; strongly recommended to be specified by PROVYS
   * StyleGuide, even though optional in Sql standard.
   */
  @Nullable QueryAlias getAlias();

  /**
   * Retrieve column type id column with such alias is available in from clause.
   *
   * @param alias is alias being looked up
   * @return type associated with given column, null if column is not found
   */
  @Nullable Class<?> getColumnType(SimpleName alias);

  /**
   *
   */
  /**
   * Appends from clause.
   *
   * @param builder is CodeBuilder, used to construct com.provys.db.sql text
   */
  void appendFrom(CodeBuilder builder);
}
