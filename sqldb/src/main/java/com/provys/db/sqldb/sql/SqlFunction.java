package com.provys.db.sqldb.sql;

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
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromContext;
import com.provys.db.sql.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FUNCTION")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
final class SqlFunction implements SqlExpression {

  @JsonProperty("FUNCTION")
  private final Function function;
  @JsonProperty("ARGUMENTS")
  @JacksonXmlProperty(localName = "ARGUMENT")
  @JacksonXmlElementWrapper(localName = "ARGUMENTS", useWrapping = true)
  private final List<SqlExpression> arguments;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

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
    for (Expression argument : arguments) {
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

  SqlFunction(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Function function,
      Collection<? extends Expression> arguments, @Nullable FromContext fromContext,
      @Nullable BindMap bindMap) {
    this.context = context;
    this.function = function;
    this.arguments = new ArrayList<>(arguments.size());
    for (Expression argument : arguments) {
      this.arguments.add(argument.transfer(context, fromContext, bindMap));
    }
    verifyArguments();
  }

  SqlFunction(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Function function, Expression[] arguments,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    this(context, function, Arrays.asList(arguments), fromContext, bindMap);
  }

  @JsonCreator
  SqlFunction(@JsonProperty("FUNCTION") Function function,
      @JsonProperty("ARGUMENTS") Collection<? extends SqlExpression> arguments) {
    this(SqlContextImpl.getNoDbInstance(), function, arguments, null, null);
  }

  @Override
  public Class<?> getType() {
    if (function.getResultAsArgument() >= 0) {
      return arguments.get(function.getResultAsArgument()).getType();
    }
    return function.getResult();
  }

  @Override
  public <E extends Expression> E transfer(Context<?, ?, ?, ?, ?, ?, E> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    if ((fromContext == null) && (bindMap == null) && targetContext.equals(context)) {
      @SuppressWarnings("unchecked")
      var result = (E) this;
      return result;
    }
    return targetContext.function(function, arguments, fromContext, bindMap);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return arguments.stream()
        .flatMap(argument -> argument.getBinds().stream())
        .collect(Collectors.toUnmodifiableList());
  }

  private static class ArgumentAppender implements Consumer<CodeBuilder> {

    private final SqlExpression argument;

    ArgumentAppender(SqlExpression argument) {
      this.argument = argument;
    }

    @Override
    public void accept(CodeBuilder builder) {
      argument.append(builder);
    }
  }

  @Override
  public void append(CodeBuilder builder) {
    List<Consumer<CodeBuilder>> argumentsAppend = arguments.stream()
        .map(ArgumentAppender::new)
        .collect(Collectors.toList());
    context.append(function, argumentsAppend, builder);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFunction that = (SqlFunction) o;
    return function == that.function
        && arguments.equals(that.arguments)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = function != null ? function.hashCode() : 0;
    result = 31 * result + arguments.hashCode();
    result = 31 * result + context.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlFunction{"
        + "function=" + function
        + ", arguments=" + arguments
        + ", context=" + context
        + '}';
  }
}
