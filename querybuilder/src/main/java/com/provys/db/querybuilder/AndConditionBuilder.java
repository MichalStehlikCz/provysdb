package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;

/**
 * Builder used to prepare condition by connecting sub-conditions using AND. Builder might be
 * mutable
 */
public interface AndConditionBuilder extends ConditionBuilder {

  /**
   * Append another condition.
   *
   * @param condition condition to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   * define if action modifies given builder or produces new builder
   */
  AndConditionBuilder and(Condition condition);
}
