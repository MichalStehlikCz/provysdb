package com.provys.db.sqldb.sql;

import static com.provys.db.sql.Function.STRING_CHR;
import static com.provys.db.sql.Function.STRING_CONCAT;

import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlLiteralNVarchar implements SqlExpression {

  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;
  private final String value;
  private final @Nullable SqlExpression expression;

  private static <E extends SqlExpression> @Nullable SqlExpression evalExpression(
      SqlContext<?, ?, ?, ?, ?, ?, E> context, String value) {
    if (value.indexOf('\n') == -1) {
      return null;
    }
    var stringParts = value.split("\n", -1);
    var parts = new ArrayList<E>(stringParts.length * 2 - 1);
    var first = true;
    for (var stringPart : stringParts) {
      if (first) {
        first = false;
      } else {
        parts.add(
            context.function(STRING_CHR, new SqlExpression[]{context.literal(10)}, null, null));
      }
      if (!stringPart.isEmpty()) {
        parts.add(context.literalNVarchar(stringPart));
      }
    }
    return context.function(STRING_CONCAT, parts, null, null);
  }

  SqlLiteralNVarchar(SqlContext<?, ?, ?, ?, ?, ?, ?> context, String value) {
    this.context = context;
    this.value = value;
    this.expression = evalExpression(context, value);
  }

  @Override
  public Class<?> getType() {
    return String.class;
  }

  @Override
  public <E extends Expression> E transfer(Context<?, ?, ?, ?, ?, ?, E> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    if (context.equals(targetContext)) {
      @SuppressWarnings("unchecked")
      var result = (E) this;
      return result;
    }
    return targetContext.literalNVarchar(value);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public void append(CodeBuilder builder) {
    if (expression == null) {
      // simple string, without newlines
      builder.append('N')
          .applyString(
              stringBuilder -> context.getSqlTypeMap().appendLiteral(stringBuilder, value));
    } else {
      expression.append(builder);
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
    SqlLiteralNVarchar that = (SqlLiteralNVarchar) o;
    return Objects.equals(context, that.context)
        && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    int result = context != null ? context.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlLiteralNVarchar{"
        + "context=" + context
        + ", value='" + value + '\''
        + '}';
  }
}
