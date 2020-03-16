package sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface FromClauseBuilder {

  /**
   * Alias associated with given expression.
   *
   * @return alias this table is associated with; strongly recommended to be specified by PROVYS
   * StyleGuide, even though optional in Sql standard.
   */
  @Nullable QueryAlias getAlias();

  /**
   * Replace alias with specified one.
   *
   * @param newAlias is new alias to be used for new from clause
   * @return from clause with alias replaced by specified one
   */
  FromClause as(QueryAlias newAlias);

  /**
   * Replace alias with specified one.
   *
   * @param newAlias is new alias to be used for new from clause
   * @return from clause with alias replaced by specified one
   */
  FromClause as(String newAlias);

}
