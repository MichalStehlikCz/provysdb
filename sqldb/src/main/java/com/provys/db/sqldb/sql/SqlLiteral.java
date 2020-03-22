package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromContext;
import com.provys.db.sqldb.dbcontext.DefaultJsonIdTypeResolver;
import com.provys.db.sqldb.dbcontext.DefaultJsonObjectDeserializer;
import com.provys.db.sqldb.dbcontext.DefaultJsonObjectSerializer;
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
final class SqlLiteral implements SqlExpression {

  private final Object value;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlLiteral(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Object value) {
    this.context = context;
    this.value = value;
  }

  @JsonCreator
  SqlLiteral(@JsonProperty("VALUE") @JsonDeserialize(using = DefaultJsonObjectDeserializer.class)
      Object value) {
    this(SqlContextImpl.getNoDbInstance(), value);
  }

  @JsonProperty("VALUE")
  @JsonSerialize(using = DefaultJsonObjectSerializer.class)
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
