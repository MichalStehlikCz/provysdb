package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import java.util.Collection;

/**
 * Builder used to construct condition by connecting subconditions using OR. Builder might be
 * mutable
 */
public interface OrConditionBuilder {

  /**
   * Append another condition.
   *
   * @param newCondition condition to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   *     define if action modifies given builder or produces new builder
   */
  OrConditionBuilder or(Condition newCondition);

  /**
   * Append collection of conditions.
   *
   * @param newConditions are conditions to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   *     define if action modifies given builder or produces new builder
   */
  OrConditionBuilder or(Collection<? extends Condition> newConditions);

  /**
   * Append condition, generated from supplied condition builder. Condition is rendered immediately
   * and later changes to supplied condition builder are ignored.
   *
   * @param newCondition condition builder evaluating to condition to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   *     define if action modifies given builder or produces new builder
   */
  OrConditionBuilder or(ConditionBuilder newCondition);
}
