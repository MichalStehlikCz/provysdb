package com.provys.db.querybuilder;

import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.functions.ConditionalOperator;
import java.util.Collection;

final class CombiningConditionBuilderOr extends CombiningConditionBuilder
    implements OrConditionBuilder {


  CombiningConditionBuilderOr(ElementFactory elementFactory) {
    super(ConditionalOperator.COND_OR, elementFactory);
  }

  @Override
  public OrConditionBuilder or(Condition newCondition) {
    add(newCondition);
    return this;
  }

  @Override
  public OrConditionBuilder or(Collection<? extends Condition> newConditions) {
    add(newConditions);
    return this;
  }

  @Override
  public OrConditionBuilder or(ConditionBuilder newCondition) {
    add(newCondition);
    return this;
  }

  @Override
  public String toString() {
    return "CombiningConditionBuilderOr{" + super.toString() + '}';
  }
}
