package com.provys.db.sqlquery.query;

import static com.provys.db.query.elements.Function.STRING_CHR;
import static com.provys.db.query.elements.Function.STRING_CONCAT;

import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.ExpressionFunction;
import com.provys.db.query.elements.Literal;
import com.provys.db.query.elements.LiteralNVarchar;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

final class LiteralNVarcharSqlBuilder
    implements ExpressionTypeSqlBuilder<SqlBuilder<?>, LiteralNVarchar> {

  private static final LiteralNVarcharSqlBuilder INSTANCE = new LiteralNVarcharSqlBuilder();

  /**
   * Access singleton instance of this builder.
   *
   * @return singleton instance
   */
  public static LiteralNVarcharSqlBuilder getInstance() {
    return INSTANCE;
  }

  private LiteralNVarcharSqlBuilder() {
  }

  @Override
  public Class<LiteralNVarchar> getType() {
    return LiteralNVarchar.class;
  }

  private static @Nullable Expression<?> evalExpression(String value) {
    if (value.indexOf('\n') == -1) {
      return null;
    }
    var stringParts = value.split("\n", -1);
    var parts = new ArrayList<Expression<?>>(stringParts.length * 2 - 1);
    var first = true;
    for (var stringPart : stringParts) {
      if (first) {
        first = false;
      } else {
        parts.add(
            new ExpressionFunction<>(String.class, STRING_CHR, List.of(new Literal<>(10))));
      }
      if (!stringPart.isEmpty()) {
        parts.add(new LiteralNVarchar(stringPart));
      }
    }
    return new ExpressionFunction<>(String.class, STRING_CONCAT, parts);
  }

  @Override
  public void append(SqlBuilder<?> sqlBuilder, LiteralNVarchar element) {
    var expression = evalExpression(element.getValue());
    if (expression == null) {
      // simple string, without newlines
      sqlBuilder.append('N')
          .appendLiteral(element.getValue());
    } else {
      // translated to more complex expression
      sqlBuilder.append(expression);
    }
  }

  @Override
  public String toString() {
    return "LiteralNVarcharSqlBuilder{}";
  }
}
