package com.provys.db.query.functions;

import com.provys.common.exception.InternalException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class RepeatingBuiltInBase extends BuiltInBase {

  /**
   * Return type is the same as the first argument type. If Object is used, it is expected that the
   * return type of expression is the same as type of argument.
   */
  private final Class<?> returnType;
  /**
   * Second argument type (might be different from the first). If object is used for both return
   * type and argument type, it is expected that all arguments must be of the same type.
   */
  private final Class<?> secondArgumentType;

  RepeatingBuiltInBase(String name, Class<?> returnType, Class<?> secondArgumentType) {
    super(name);
    this.returnType = returnType;
    this.secondArgumentType = secondArgumentType;
  }

  private InternalException atLeastOneArgumentExpected() {
    return new InternalException(validatePrefix() + "at least one argument expected");
  }

  @Override
  public Class<?> getReturnType(List<? extends Class<?>> callArguments) {
    if (returnType == Object.class) {
      // return type defined by the first argument
      if (callArguments.isEmpty()) {
        throw atLeastOneArgumentExpected();
      }
      return callArguments.get(0);
    }
    return returnType;
  }

  @Override
  public Class<?> getReturnType(Stream<? extends Class<?>> callArguments) {
    if (returnType == Object.class) {
      // return type defined by the first argument
      return callArguments.findFirst().orElseThrow(this::atLeastOneArgumentExpected);
    }
    return returnType;
  }

  @Override
  protected @Nullable String validateArgumentsInt(List<? extends Class<?>> callArguments) {
    // verify arguments are not empty
    if (callArguments.isEmpty()) {
      return "at least one argument expected";
    }
    // verify first argument against return type
    if (!returnType.isAssignableFrom(callArguments.get(0))) {
      return "invalid type of first argument, got " + callArguments.get(0) + ", required "
          + returnType;
    }
    Class<?> requiredType = secondArgumentType;
    if ((requiredType == Object.class) && (returnType == Object.class)) {
      requiredType = callArguments.get(0);
    }
    // verify remaining arguments
    for (var argument : callArguments.subList(1, callArguments.size())) {
      if (!requiredType.isAssignableFrom(argument)) {
        return "invalid type of argument, got " + argument + ", required " + requiredType;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "RepeatingBuiltInBase{"
        + "returnType=" + returnType
        + ", secondArgumentType=" + secondArgumentType
        + ", " + super.toString() + '}';
  }
}
