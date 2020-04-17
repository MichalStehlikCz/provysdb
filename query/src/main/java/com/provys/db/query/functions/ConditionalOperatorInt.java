package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DbBoolean;
import java.util.List;
import java.util.stream.Stream;

/**
 * Defines conditional operator behaviour. Used by ConditionalOperator - enum that is visible from
 * outside and represents actual instances, and internal classes that actually implement behaviour
 * of conditional operator.
 */
@Immutable
interface ConditionalOperatorInt extends BuiltInInt {

  @Override
  default Class<?> getReturnType(List<? extends Class<?>> callArguments) {
    return DbBoolean.class;
  }

  @Override
  default Class<?> getReturnType(Stream<? extends Class<?>> callArguments) {
    return DbBoolean.class;
  }

  default boolean verifyReturnType(Class<?> returnType) {
    return returnType == DbBoolean.class;
  }

  @Override
  default boolean verifyReturnType(Class<?> returnType, List<? extends Class<?>> callArguments) {
    return verifyReturnType(returnType);
  }

  @Override
  default boolean verifyReturnType(Class<?> returnType, Stream<? extends Class<?>> callArguments) {
    return verifyReturnType(returnType);
  }
}
