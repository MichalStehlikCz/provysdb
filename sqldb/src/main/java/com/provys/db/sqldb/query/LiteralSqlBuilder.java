package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Literal;
import com.provys.db.sqldb.codebuilder.CodeBuilder;

public final class LiteralSqlBuilder implements ExpressionSqlBuilder<StatementFactory, Literal<?>> {

  private static final LiteralSqlBuilder INSTANCE = new LiteralSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static LiteralSqlBuilder getInstance() {
    return INSTANCE;
  }

  private LiteralSqlBuilder() {
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<Literal<?>> getType() {
    return (Class<Literal<?>>) (Class<?>) Literal.class;
  }

  /**
   * Append sql text, associated with supplied statement.
   *
   * @param sqlBuilder is builder owning context and {@link CodeBuilder} statement should be
   *                   appended to
   * @param expression is expression that is being appended
   */
  @Override
  public void append(SqlBuilder<? extends StatementFactory, ?, ?> sqlBuilder,
      Literal<?> expression) {
    sqlBuilder.getCodeBuilder()
        .applyString(
            stringBuilder -> sqlBuilder.getStatementFactory().getDbContext().getSqlTypeMap()
                .appendLiteral(stringBuilder, expression.getValue()));
  }

  @Override
  public String toString() {
    return "LiteralSqlBuilder{}";
  }
}
