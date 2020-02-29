package com.provys.provysdb.sqlbuilder;

/**
 * Builder class for combining multiple conditions using AND or OR operands.
 */
public interface ConditionJoiner {

  /**
   * Add condition to joiner.
   *
   * @param condition is condition to be added
   * @return self to allow fluent build
   */
  ConditionJoiner add(Condition condition);

  /**
   * Build condition from joined conditions.
   *
   * @return resulting combined (constant) where condition
   */
  Condition build();
}
