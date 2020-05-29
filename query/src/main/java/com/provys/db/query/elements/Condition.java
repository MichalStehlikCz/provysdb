package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DbBoolean;
import com.provys.db.query.names.BindMap;

@Immutable
public interface Condition extends Expression<DbBoolean> {

  @Override
  default Class<DbBoolean> getType() {
    return DbBoolean.class;
  }

  @Override
  Condition mapBinds(BindMap bindMap);

  /**
   * Apply condition on consumer. Allows application on condition consumer, while generic element
   * only supports QueryConsumer.
   *
   * @param consumer is consumer this condition should be applied to
   */
  void apply(ConditionConsumer consumer);

  @Override
  default void apply(ExpressionConsumer consumer) {
    apply((ConditionConsumer) consumer);
  }

  @Override
  default void apply(QueryConsumer consumer) {
    apply((ConditionConsumer) consumer);
  }
}
