package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;

public interface ConditionBuilder {

  /**
   * Build condition prepared by this builder.
   *
   * @return condition prepared by this builder
   */
  Condition build();
}
