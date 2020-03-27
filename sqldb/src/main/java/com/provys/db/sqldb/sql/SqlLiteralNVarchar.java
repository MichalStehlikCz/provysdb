package com.provys.db.sqldb.sql;

import static com.provys.db.sql.Function.STRING_CHR;
import static com.provys.db.sql.Function.STRING_CONCAT;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.Nullable;

@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("LITERALNVARCHAR")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
final class SqlLiteralNVarchar implements SqlExpression {

  @JsonProperty("VALUE")
  private final String value;
  private final @Nullable SqlExpression expression;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

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

  /**
   * Constructor used for deserialisation.
   *
   * @param value is string value, represented by this literal
   */
  @JsonCreator
  SqlLiteralNVarchar(@JsonProperty("VALUE") String value) {
    this(SqlContextImpl.getNoDbInstance(), value);
  }

  @Override
  public Class<?> getType() {
    return String.class;
  }

  /**
   * Value of field value.
   *
   * @return value of field value
   */
  String getValue() {
    return value;
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
    return value.equals(that.value)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = value.hashCode();
    result = 31 * result + context.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlLiteralNVarchar{"
        + "value='" + value + '\''
        + ", context=" + context
        + '}';
  }
}
