package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;

/**
 * Builder used to construct condition by connecting subconditions using OR. Builder might be
 * mutable
 */
public interface OrConditionBuilder {

  /**
   * Append another condition.
   *
   * @param condition condition to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   * define if action modifies given builder or produces new builder
   */
  OrConditionBuilder or(Condition condition);
}
