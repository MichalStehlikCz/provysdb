package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Expression;
import com.provys.db.sql.FromContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("ValidExternallyBoundObject")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(
    propOrder = {"value"}
)
@XmlRootElement(
    name = "LITERAL"
)
final class SqlLiteral implements SqlExpression {

  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;
  @XmlElement(
      name = "VALUE"
  )
  private final Object value;

  SqlLiteral(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Object value) {
    this.context = context;
    this.value = value;
  }

  @JsonCreator
  SqlLiteral(@JsonProperty("VALUE") Object value) {
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
    return "SqlLiteral{"
        + "context=" + context
        + ", value=" + value
        + '}';
  }
}
