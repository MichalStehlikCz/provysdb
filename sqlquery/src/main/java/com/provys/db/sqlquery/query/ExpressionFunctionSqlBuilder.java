package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.ExpressionFunction;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class ExpressionFunctionSqlBuilder
    implements ExpressionTypeSqlBuilder<SqlBuilder<?>, ExpressionFunction<?>> {

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

  private static class ArgumentAppender implements Consumer<SqlBuilder<?>> {

    private final SqlBuilder<?> sqlBuilder;
    private final Expression<?> argument;

    ArgumentAppender(SqlBuilder<?> sqlBuilder, Expression<?> argument) {
      this.sqlBuilder = sqlBuilder;
      this.argument = argument;
    }

    @Override
    public void accept(SqlBuilder builder) {
      sqlBuilder.append(argument);
    }
  }

  /**
   * Append sql text, associated with supplied element.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param element    is element that is being appended
   */
  @Override
  public void append(SqlBuilder<?> sqlBuilder, ExpressionFunction<?> element) {
    List<Consumer<? super SqlBuilder<?>>> argumentsAppend = element.getArguments().stream()
        .map(argument -> new ArgumentAppender(sqlBuilder, argument))
        .collect(Collectors.toList());
    sqlBuilder.append(element.getFunction(), argumentsAppend);
  }

  @Override
  public String toString() {
    return "ExpressionFunctionSqlBuilder{}";
  }
}
