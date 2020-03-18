package com.provys.db.sqldb.sql;

import com.provys.common.exception.InternalException;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlFunction implements SqlExpression {

  /**
   * Used to specify type in list-to-array conversion.
   */
  public static final Expression[] EXPRESSION0 = new SqlExpression[]{};

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

  SqlFunction(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Function function, Expression[] arguments,
      @Nullable BindMap bindMap) {
    this.context = context;
    this.function = function;
    this.arguments = new ArrayList<>(arguments.length);
    for (Expression argument : arguments) {
      this.arguments.add(argument.transfer(context, bindMap));
    }
    verifyArguments();
  }

  SqlFunction(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Function function,
      List<Expression> arguments, @Nullable BindMap bindMap) {
    this(context, function, arguments.toArray(EXPRESSION0), bindMap);
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
      @Nullable BindMap bindMap) {
    if (context.equals(targetContext) && ((bindMap == null) || bindMap.isSupersetOf(getBinds()))) {
      @SuppressWarnings("unchecked")
      var result = (E) this;
      return result;
    }
    return targetContext.function(function, arguments, bindMap);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Arrays.stream(arguments)
        .flatMap(argument -> argument.getBinds().stream())
        .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Append expression to code builder. Used to append expression to its default place; some
   * elements might also have secondary place (e.g. join clause might want to add additional hint).
   * Such situations are solved by additional procedures, specific for given type of statement.
   *
   * @param builder is code builder to which sql text should be appended
   */
  @Override
  public void append(CodeBuilder builder) {
    var template = context.getFunctionTemplate(function);
    var argumentsInTemplate = function.getArguments().size();
    var result = template;
    for (var i = 0; i < argumentsInTemplate; i++) {
      var argumentBuilder = CodeBuilderFactory.getCodeBuilder();
      arguments[i].append
      result = result.replace('{' + Integer.toString(i) + '}', )
    }
    if (function.lastArgumentRepeatable()) {

    }
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
    return Objects.equals(context, that.context)
        && function == that.function
        && Arrays.equals(arguments, that.arguments);
  }

  @Override
  public int hashCode() {
    int result = context != null ? context.hashCode() : 0;
    result = 31 * result + (function != null ? function.hashCode() : 0);
    result = 31 * result + Arrays.hashCode(arguments);
    return result;
  }

  @Override
  public String toString() {
    return "SqlFunction{"
        + "context=" + context
        + ", function=" + function
        + ", arguments=" + Arrays.toString(arguments)
        + '}';
  }
}
