package com.provys.db.querybuilder;

import com.provys.common.exception.InternalException;
import com.provys.db.query.elements.Condition;
import com.provys.db.query.elements.ConditionConsumer;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.functions.ConditionalOperator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class CombiningConditionBuilder implements ConditionBuilder {

  private final ConditionalOperator operator;
  private final List<Condition> conditions;
  private final ElementFactory elementFactory;

  CombiningConditionBuilder(ConditionalOperator operator, ElementFactory elementFactory) {
    this.operator = operator;
    this.conditions = new ArrayList<>(3);
    this.elementFactory = elementFactory;
  }

  /**
   * Value of field operator.
   *
   * @return value of field operator
   */
  ConditionalOperator getOperator() {
    return operator;
  }

  /**
   * Value of field conditions.
   *
   * @return value of field conditions, returned as unmodifiable list
   */
  List<Condition> getConditions() {
    return Collections.unmodifiableList(conditions);
  }

  /**
   * Value of field elementFactory.
   *
   * @return value of field elementFactory
   */
  ElementFactory getElementFactory() {
    return elementFactory;
  }

  @Override
  public void appendBinds(BindVariableCombiner combiner) {
    combiner.addElements(conditions);
  }

  /**
   * Allows to extract sub-conditions from combined condition with same operator as this builder.
   */
  private static final class Consumer implements ConditionConsumer {

    private final ConditionalOperator operator;
    private @MonotonicNonNull Collection<Condition> subConditions;

    private Consumer(ConditionalOperator operator) {
      this.operator = operator;
    }

    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    @Override
    public void condition(ConditionalOperator condOperator,
        Collection<? extends Expression<?>> arguments) {
      if (condOperator == operator) {
        subConditions = new ArrayList<>(arguments.size());
        for (var argument : arguments) {
          if (argument instanceof Condition) {
            subConditions.add((Condition) argument);
          } else {
            throw new InternalException("Arguments to " + operator + " expected to be conditions");
          }
        }
      }
    }
  }

  void add(@Nullable Condition condition) {
    if (condition == null) {
      return;
    }
    // we want to check if condition is not in fact AND - as in that case, we would like to expand
    // condition to individual sub-conditions
    var consumer = new Consumer(operator);
    condition.apply(consumer);
    if (consumer.subConditions != null) {
      add(consumer.subConditions);
      return;
    }
    conditions.add(condition);
  }

  void add(Iterable<? extends Condition> newConditions) {
    for (var newCondition : newConditions) {
      add(newCondition);
    }
  }

  void add(ConditionBuilder conditionBuilder) {
    if (conditionBuilder instanceof CombiningConditionBuilder) {
      var combiningConditionBuilder = (CombiningConditionBuilder) conditionBuilder;
      if (combiningConditionBuilder.getOperator() == operator) {
        // we can include conditions directly...
        for (var subCondition : combiningConditionBuilder.getConditions()) {
          add(subCondition);
        }
        return;
      }
    }
    conditionBuilder.buildOpt()
        .ifPresent(this::add);
  }

  @Override
  public @Nullable Condition build() {
    if (conditions.isEmpty()) {
      return null;
    }
    if (conditions.size() == 1) {
      return conditions.get(0);
    }
    return elementFactory.condition(operator, conditions);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CombiningConditionBuilder that = (CombiningConditionBuilder) o;
    return operator == that.operator
        && conditions.equals(that.conditions)
        && elementFactory.equals(that.elementFactory);
  }

  @Override
  public int hashCode() {
    int result = operator.hashCode();
    result = 31 * result + conditions.hashCode();
    result = 31 * result + elementFactory.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "CombiningConditionBuilder{"
        + "operator=" + operator
        + ", conditions=" + conditions
        + ", elementFactory=" + elementFactory
        + '}';
  }
}
