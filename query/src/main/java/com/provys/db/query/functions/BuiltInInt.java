package com.provys.db.query.functions;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import java.util.List;
import java.util.stream.Stream;

@Immutable
public interface BuiltInInt {

  /**
   * Built-in name. Should be unique, in practice implemented via Enum as all actual instances
   * available are in fact enums.
   *
   * @return name of built-in function / conditional operator
   */
  String name();

  /**
   * Evaluate return type of built-in given list of argument types.
   *
   * @param callArguments is list of argument types
   * @return type of value expression evaluates to
   */
  Class<?> getReturnType(List<? extends Class<?>> callArguments);

  /**
   * Evaluate return type of built-in given stream of argument types. Might be easier to use if
   * argument types are wrapped
   *
   * @param callArguments is list of argument types
   * @return type of value expression evaluates to
   */
  Class<?> getReturnType(Stream<? extends Class<?>> callArguments);

  /**
   * Verify if desired return type is valid for function with given argument types.
   *
   * @param returnType    is suggested return type
   * @param callArguments is list of argument types
   * @return true if function result value can be assigned to given return type (is same or subtype)
   */
  default boolean verifyReturnType(Class<?> returnType, List<? extends Class<?>> callArguments) {
    return returnType.isAssignableFrom(getReturnType(callArguments));
  }

  /**
   * Verify if desired return type is valid for function with given argument types.
   *
   * @param returnType    is suggested return type
   * @param callArguments is stream of argument types
   * @return true if function result value can be assigned to given return type (is same or subtype)
   */
  default boolean verifyReturnType(Class<?> returnType, Stream<? extends Class<?>> callArguments) {
    return returnType.isAssignableFrom(getReturnType(callArguments));
  }

  /**
   * Validate if desired return type is valid for function with given argument types.
   *
   * @param callReturnType is suggested return type
   * @param callArguments  is list of argument types
   * @throws InternalException when function result is not compatible with required return type
   */
  void validateReturnType(Class<?> callReturnType, List<? extends Class<?>> callArguments);

  /**
   * Validate if desired return type is valid for function with given argument types.
   *
   * @param callReturnType is suggested return type
   * @param callArguments  is list of argument types
   * @throws InternalException when function result is not compatible with required return type
   */
  void validateReturnType(Class<?> callReturnType, Stream<? extends Class<?>> callArguments);

  /**
   * Verify if supplied argument types are compatible with function call.
   *
   * @param callArguments are types of arguments to be passed to function
   * @return true if arguments are compatible with function, false otherwise
   */
  boolean verifyArguments(List<? extends Class<?>> callArguments);

  /**
   * Verify if supplied argument types are compatible with function call.
   *
   * @param callArguments are types of arguments to be passed to function
   * @return true if arguments are compatible with function, false otherwise
   */
  boolean verifyArguments(Stream<? extends Class<?>> callArguments);

  /**
   * Validate if supplied argument types are compatible with function call.
   *
   * @param callArguments are types of arguments to be passed to function
   * @throws InternalException when arguments are not compatible with function
   */
  void validateArguments(List<? extends Class<?>> callArguments);

  /**
   * Validate if supplied argument types are compatible with function call.
   *
   * @param callArguments are types of arguments to be passed to function
   * @throws InternalException when arguments are not compatible with function
   */
  void validateArguments(Stream<? extends Class<?>> callArguments);
}
