package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.ExpressionFunction;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ExpressionFunctionSqlBuilder
    implements ExpressionSqlBuilder<StatementFactory<?>, ExpressionFunction<?>> {

  private static final ExpressionFunctionSqlBuilder INSTANCE = new ExpressionFunctionSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static ExpressionFunctionSqlBuilder getInstance() {
    return INSTANCE;
  }

  private ExpressionFunctionSqlBuilder() {
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<ExpressionFunction<?>> getType() {
    return (Class<ExpressionFunction<?>>) (Class<?>) ExpressionFunction.class;
  }

  private static class ArgumentAppender implements Consumer<CodeBuilder> {

    private final SqlBuilder<? extends StatementFactory<?>, ?, ?> sqlBuilder;
    private final Expression<?> argument;

    ArgumentAppender(SqlBuilder<? extends StatementFactory<?>, ?, ?> sqlBuilder, Expression<?> argument) {
      this.sqlBuilder = sqlBuilder;
      this.argument = argument;
    }

    @Override
    public void accept(CodeBuilder builder) {
      sqlBuilder.getStatementFactory().getElementBuilder(argument).append(sqlBuilder, argument);
      argument.append(builder);
    }
  }

  @Override
  public void append(SqlBuilder<? extends StatementFactory<?>, ?, ?> sqlBuilder,
      ExpressionFunction<?> element) {
    List<Consumer<CodeBuilder>> argumentsAppend = element.getArguments().stream()
        .map(argument -> new ArgumentAppender(sqlBuilder, argument))
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
    ExpressionFunctionSqlBuilder that = (ExpressionFunctionSqlBuilder) o;
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
