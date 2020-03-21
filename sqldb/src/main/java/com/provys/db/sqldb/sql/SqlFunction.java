package com.provys.db.sqldb.sql;

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

final class SqlFunction implements SqlExpression {

  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;
  private final Function function;
  private final List<SqlExpression> arguments;

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
    return context.equals(that.context)
        && function == that.function
        && arguments.equals(that.arguments);
  }

  @Override
  public int hashCode() {
    int result = context.hashCode();
    result = 31 * result + (function != null ? function.hashCode() : 0);
    result = 31 * result + arguments.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlFunction{"
        + "context=" + context
        + ", function=" + function
        + ", arguments=" + arguments
        + '}';
  }
}
