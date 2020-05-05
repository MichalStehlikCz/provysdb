package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Builder used to prepare condition by connecting sub-conditions using AND. Builder might be
 * mutable
 */
public interface AndConditionBuilder extends ConditionBuilder {

  /**
   * Append another condition.
   *
   * @param newCondition condition to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   *     define if action modifies given builder or produces new builder
   */
  AndConditionBuilder and(@Nullable Condition newCondition);

  /**
   * Append conditions from supplied collection.
   *
   * @param newConditions are conditions to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   *     define if action modifies given builder or produces new builder
   */
  AndConditionBuilder and(Collection<? extends Condition> newConditions);

  /**
   * Append condition, represented by supplied builder. Note that condition is built immediatelly
   * and later changes to supplied builder are ignored
   *
   * @param newCondition is builder with condition to be appended
   * @return condition builder with appended condition to support fluent build. Interface does not
   *     define if action modifies given builder or produces new builder
   */
  AndConditionBuilder and(ConditionBuilder newCondition);
}
