package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import java.util.List;
import java.util.stream.Stream;

/**
 * Used to manage lifecycle of conditional operator instances. Only these instances are used
 * anywhere in system, even though they relay on using actual implementations of
 * ConditionalOperatorInt interface for their functionality - but these are not exposed outside of
 * this package.
 */
@Immutable
public enum ConditionalOperator implements BuiltIn, ConditionalOperatorInt {

  COND_OR(new RepeatingConditionalOperator("COND_OR")),
  COND_AND(new RepeatingConditionalOperator("COND_AND")),
  COND_NOT(new NotOperator());

  private final ConditionalOperatorInt impl;

  ConditionalOperator(ConditionalOperatorInt impl) {
    this.impl = impl;
  }

  @Override
  public Class<?> getReturnType(List<? extends Class<?>> callArguments) {
    return impl.getReturnType(callArguments);
  }

  @Override
  public Class<?> getReturnType(Stream<? extends Class<?>> callArguments) {
    return impl.getReturnType(callArguments);
  }

  @Override
  public boolean verifyReturnType(Class<?> returnType,
      List<? extends Class<?>> callArguments) {
    return impl.verifyReturnType(returnType, callArguments);
  }

  @Override
  public void validateReturnType(Class<?> callReturnType,
      List<? extends Class<?>> callArguments) {
    impl.validateReturnType(callReturnType, callArguments);
  }

  @Override
  public void validateReturnType(Class<?> callReturnType,
      Stream<? extends Class<?>> callArguments) {
    impl.validateReturnType(callReturnType, callArguments);
  }

  @Override
  public boolean verifyArguments(List<? extends Class<?>> callArguments) {
    return impl.verifyArguments(callArguments);
  }

  @Override
  public boolean verifyArguments(Stream<? extends Class<?>> callArguments) {
    return impl.verifyArguments(callArguments);
  }

  @Override
  public void validateArguments(List<? extends Class<?>> callArguments) {
    impl.validateArguments(callArguments);
  }

  @Override
  public void validateArguments(Stream<? extends Class<?>> callArguments) {
    impl.validateArguments(callArguments);
  }
}
