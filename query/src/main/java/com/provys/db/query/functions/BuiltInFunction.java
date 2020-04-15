package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DtDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * Used to manage lifecycle of built-in function instances. Only these instances are used
 * anywhere in system, even though they relay on using actual implementations of
 * BuiltInFunctionInt interface for their functionality - but these are not exposed outside of
 * this package.
 */
@Immutable
public enum BuiltInFunction implements BuiltIn, BuiltInFunctionInt {

  STRING_CHR(new PlainFunction("STRING_CHR", String.class, Integer.class)),
  STRING_CONCAT(new RepeatingBuiltInFunction("STRING_CONCAT", String.class, String.class)),
  DATE_SYSDATE(new PlainFunction("DATE_SYSDATE", DtDateTime.class)),
  ANY_NVL(new RepeatingBuiltInFunction("ANY_NVL", Object.class, Object.class));

  private final BuiltInFunctionInt impl;

  BuiltInFunction(BuiltInFunctionInt impl) {
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
  public void validateReturnType(Class<?> callReturnType, List<? extends Class<?>> callArguments) {
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
