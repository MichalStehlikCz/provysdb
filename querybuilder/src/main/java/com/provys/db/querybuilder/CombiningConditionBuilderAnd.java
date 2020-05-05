package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.functions.ConditionalOperator;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

final class CombiningConditionBuilderAnd extends CombiningConditionBuilder
    implements AndConditionBuilder {

  CombiningConditionBuilderAnd(ElementFactory elementFactory) {
    super(ConditionalOperator.COND_AND, elementFactory);
  }

  @Override
  public AndConditionBuilder and(@Nullable Condition newCondition) {
    add(newCondition);
    return this;
  }

  @Override
  public AndConditionBuilder and(Collection<? extends Condition> newConditions) {
    add(newConditions);
    return this;
  }

  @Override
  public AndConditionBuilder and(ConditionBuilder newCondition) {
    add(newCondition);
    return this;
  }

  @Override
  public String toString() {
    return "CombiningConditionBuilderAnd{" + super.toString() + '}';
  }
}
