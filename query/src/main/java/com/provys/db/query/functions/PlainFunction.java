package com.provys.db.query.functions;

import java.util.List;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;

class PlainFunction extends BuiltInBase implements BuiltInFunctionInt {

  private final Class<?> returnType;
  // list is immutable as product of List.of, contains immutable class
  @SuppressWarnings("Immutable")
  private final List<Class<?>> arguments;

  PlainFunction(String name, Class<?> returnType, Class<?>... arguments) {
    super(name);
    this.returnType = returnType;
    this.arguments = List.of(arguments);
  }

  @Override
  public Class<?> getReturnType(List<? extends Class<?>> callArguments) {
    return returnType;
  }

  @Override
  public Class<?> getReturnType(Stream<? extends Class<?>> callArguments) {
    return returnType;
  }

  @Override
  protected @Nullable String validateArgumentsInt(List<? extends Class<?>> callArguments) {
    // verify number of arguments
    if (arguments.size() != callArguments.size()) {
      return "incorrect number of arguments - expected " + arguments.size() + ", supplied "
          + callArguments.size();
    }
    // verify argument types
    var argumentIterator = arguments.iterator();
    var callArgumentIterator = callArguments.iterator();
    while (argumentIterator.hasNext()) {
      var argument = argumentIterator.next();
      var callArgument = callArgumentIterator.next();
      if (!argument.isAssignableFrom(callArgument)) {
        return "invalid type of argument, got " + callArgument + ", required " + argument;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "PlainFunction{"
        + "returnType=" + returnType
        + ", arguments=" + arguments
        + ", " + super.toString() + '}';
  }
}
