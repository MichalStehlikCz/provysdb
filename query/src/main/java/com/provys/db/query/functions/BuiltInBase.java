package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Common ancestor for built-ins.
 */
@Immutable
abstract class BuiltInBase implements BuiltInInt {

  private final String name;

  BuiltInBase(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return name;
  }

  protected String validatePrefix() {
    return "Function " + name() + ": ";
  }

  @Override
  public void validateReturnType(Class<?> callReturnType, List<? extends Class<?>> callArguments) {
    if (!verifyReturnType(callReturnType, callArguments)) {
      throw new InternalException(validatePrefix() + "return type not compatible with "
          + ", required type " + callReturnType);
    }
  }

  @Override
  public void validateReturnType(Class<?> callReturnType,
      Stream<? extends Class<?>> callArguments) {
    if (!verifyReturnType(callReturnType, callArguments)) {
      throw new InternalException(validatePrefix() + "return type not compatible with "
          + ", required type " + callReturnType);
    }
  }

  protected abstract @Nullable String validateArgumentsInt(List<? extends Class<?>> callArguments);

  @Override
  public boolean verifyArguments(List<? extends Class<?>> callArguments) {
    return validateArgumentsInt(callArguments) == null;
  }

  @Override
  public boolean verifyArguments(Stream<? extends Class<?>> callArguments) {
    return verifyArguments(callArguments.collect(Collectors.toList()));
  }

  @Override
  public void validateArguments(List<? extends Class<?>> callArguments) {
    var message = validateArgumentsInt(callArguments);
    if (message != null) {
      throw new InternalException(validatePrefix() + message);
    }
  }

  @Override
  public void validateArguments(Stream<? extends Class<?>> callArguments) {
    validateArguments(callArguments.collect(Collectors.toList()));
  }

  @Override
  public <B> List<Consumer<? super B>> unnestCall(BuiltInBuildCall<B> call, List<? extends Consumer<? super B>> argumentAppend, B builder) {
    return Collections.unmodifiableList(argumentAppend);
  }

  @Override
  public String toString() {
    return "BuiltInBase{"
        + "name='" + name + '\''
        + '}';
  }
}
