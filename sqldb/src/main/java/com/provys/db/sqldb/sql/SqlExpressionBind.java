package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindVariable;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.Expression;
import com.provys.db.query.FromContext;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("BINDEXPRESSION")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
final class SqlExpressionBind implements SqlExpression {

  @JsonUnwrapped
  private final BindVariable bindVariable;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlExpressionBind(SqlContext<?, ?, ?, ?, ?, ?, ?> context, BindVariable bindVariable) {
    this.context = context;
    this.bindVariable = bindVariable;
  }

  @JsonCreator
  SqlExpressionBind(@JsonUnwrapped BindVariable bindVariable) {
    this(SqlContextImpl.getNoDbInstance(), bindVariable);
  }

  @Override
  public Class<?> getType() {
    return bindVariable.getType();
  }

  @Override
  public <E extends Expression> E transfer(Context<?, ?, ?, ?, ?, ?, E> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    if (targetContext.equals(context)
        && ((bindMap == null) || bindMap.get(bindVariable.getName()).equals(bindVariable))) {
      @SuppressWarnings("unchecked")
      var result = (E) this;
      return result;
    }
    if (bindMap != null) {
      return targetContext.bindExpression(bindVariable.getName(), bindMap);
    }
    return targetContext.bindExpression(bindVariable);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return List.of(bindVariable);
  }

  @Override
  public void append(CodeBuilder builder) {
    builder.append('?');
    builder.addBind(bindVariable);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlExpressionBind that = (SqlExpressionBind) o;
    return bindVariable.equals(that.bindVariable)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = bindVariable.hashCode();
    result = 31 * result + context.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlExpressionBind{"
        + "bindVariable=" + bindVariable
        + ", context=" + context
        + '}';
  }
}
