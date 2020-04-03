package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Expression based on built-in function.
 *
 * @param <T> is type of values expression produces.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FUNCTION")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
public final class ExpressionFunction<T> implements Expression<T> {

  /**
   * Type of resulting expression. It can be evaluated from function and arguments and thus is not
   * serialized, but we still prefer to have variable here to make compile time type checking
   * smoother
   */
  private final Class<T> type;
  @JsonProperty("FUNCTION")
  private final Function function;
  @JsonProperty("ARGUMENTS")
  @JacksonXmlProperty(localName = "ARGUMENT")
  @JacksonXmlElementWrapper(localName = "ARGUMENTS", useWrapping = true)
  private final List<Expression<?>> arguments;

  /**
   * Evaluate return type of expression based on function given function and list of arguments.
   *
   * @param function is function expression is based on
   * @param arguments is list of expressions passed as arguments to function
   * @return type of value expression evaluates to
   */
  public static Class<?> getReturnType(Function function, List<? extends Expression<?>> arguments) {
    if (function.getResultAsArgument() >= 0) {
      return arguments.get(function.getResultAsArgument()).getType();
    }
    return function.getResult();
  }

  /**
   * Create function expression based on function and arguments; type is inferred from function and
   * supplied arguments.
   *
   * @param function  is function expression is based on
   * @param arguments is list of expressions passed as arguments to function
   * @return new expression, based on supplied function and arguments
   */
  @JsonCreator
  public static ExpressionFunction<?> ofFunction(@JsonProperty("FUNCTION") Function function,
      @JsonProperty("ARGUMENTS") List<? extends Expression<?>> arguments) {
    return new ExpressionFunction<>(getReturnType(function, arguments), function, arguments);
  }

  private void verifyReturnType() {
    Class<?> returnType;
    if (function.getResultAsArgument() >= 0) {
      returnType = arguments.get(function.getResultAsArgument()).getType();
    } else {
      returnType = function.getResult();
    }
    if (!type.isAssignableFrom(returnType)) {
      throw new InternalException("Function return type not compatible with required "
          + "expression type");
    }
  }

  private void verifyArguments() {
    var argumentTypes = function.getArguments();
    if (argumentTypes.size() > arguments.size()) {
      throw new InternalException(
          "Insufficient arguments for function " + function + ": " + arguments);
    }
    if ((argumentTypes.size() < arguments.size()) && !function.lastArgumentRepeatable()) {
      throw new InternalException(
          "Too many arguments for function " + function + " :" + arguments);
    }
    // nothing to check if there are no arguments
    if (arguments.isEmpty()) {
      return;
    }
    var argumentIterator = argumentTypes.iterator();
    // we have at least one argument, thus this statement does not fail
    Class<?> argumentType = argumentIterator.next();
    for (Expression<?> argument : arguments) {
      if (!argumentType.isAssignableFrom(argument.getType())) {
        throw new InternalException(
            "Invalid argument type in function " + function + "; expected " + argumentType
                + ", passed " + argument);
      }
      if (argumentIterator.hasNext()) {
        argumentType = argumentIterator.next();
      }
    }
  }

  /**
   * Create function that will evaluate to supplied type, based on supplied function and using
   * arguments. Type of function and arguments are validated against function definition.
   *
   * @param type is type of object expression returns
   * @param function is built-in function, used for evaluation
   * @param arguments are arguments to be passed to function
   */
  public ExpressionFunction(Class<T> type, Function function,
      Collection<? extends Expression<?>> arguments) {
    this.type = type;
    this.function = function;
    this.arguments = List.copyOf(arguments);
    verifyReturnType();
    verifyArguments();
  }

  /**
   * Value of field function.
   *
   * @return value of field function
   */
  public Function getFunction() {
    return function;
  }

  /**
   * Value of field arguments.
   *
   * @return value of field arguments
   */
  public List<Expression<?>> getArguments() {
    return arguments;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    BindVariableCollector binds = new BindVariableCollector();
    for (var argument : arguments) {
      binds.add(argument);
    }
    return binds.getBinds();
  }

  @Override
  public Expression<T> mapBinds(BindMap bindMap) {
    var newArguments = arguments.stream()
        .map(argument -> argument.mapBinds(bindMap))
        .collect(Collectors.toList());
    if (arguments.equals(newArguments)) {
      return this;
    }
    return new ExpressionFunction<>(type, function, newArguments);
  }

  /**
   * Type is intentionally not included in comparison, as regardless of declared type, expression
   * evaluates to the same result and thus type is not part of expression signature.
   *
   * @param o is other object to be compared
   * @return true if both expressions are equivalent, false otherwise
   */
  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExpressionFunction<?> that = (ExpressionFunction<?>) o;
    return function == that.function
        && arguments.equals(that.arguments);
  }

  @Override
  public int hashCode() {
    int result = function.hashCode();
    result = 31 * result + arguments.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ExpressionFunction{"
        + "type=" + type
        + ", function=" + function
        + ", arguments=" + arguments
        + '}';
  }
}
