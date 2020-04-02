package com.provys.db.sqldb.queryold;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindVariable;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.Expression;
import com.provys.db.query.FromContext;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
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
@JsonRootName("LITERAL")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
@JsonSerialize(using = SqlLiteralSerializer.class)
@JsonDeserialize(using = SqlLiteralDeserializer.class)
final class SqlLiteral implements SqlExpression {

  private final Object value;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlLiteral(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Object value) {
    this.context = context;
    this.value = value;
  }

  SqlLiteral(Object value) {
    this(SqlContextImpl.getNoDbInstance(), value);
  }

  Object getValue() {
    return value;
  }

  @Override
  public Class<?> getType() {
    return value.getClass();
  }

  @Override
  public <E extends Expression> E transfer(Context<?, ?, ?, ?, ?, ?, E> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    if (targetContext.equals(context)) {
      @SuppressWarnings("unchecked")
      var result = (E) this;
      return result;
    }
    return targetContext.literal(value);
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
    builder
        .applyString(stringBuilder -> context.getSqlTypeMap().appendLiteral(stringBuilder, value));
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlLiteral that = (SqlLiteral) o;
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
    return "SqlLiteral{"
        + "value=" + value
        + ", context=" + context
        + '}';
  }
}
