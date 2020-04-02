package com.provys.db.sqldb.queryold;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindVariable;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.Expression;
import com.provys.db.query.FromContext;
import com.provys.db.query.SelectColumn;
import com.provys.db.query.SimpleName;
import com.provys.db.sqldb.codebuilder.CodeBuilder;
import java.util.Collection;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents "normal" select statement column, built on expression.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("COLUMN")
@JsonTypeInfo(use = Id.NONE)
    // Needed to prevent inheritance from SqlFromElement
class SqlColumnExpression implements SqlSelectColumn {

  @JsonProperty("EXPRESSION")
  private final SqlExpression expression;
  @JsonProperty("ALIAS")
  private final @Nullable SimpleName alias;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlColumnExpression(SqlContext<?, ?, ?, ?, ?, ?, ?> context, Expression expression,
      @Nullable SimpleName alias, @Nullable FromContext fromContext,
      @Nullable BindMap bindMap) {
    this.context = context;
    this.expression = expression.transfer(context, fromContext, bindMap);
    this.alias = alias;
  }

  @JsonCreator
  SqlColumnExpression(@JsonProperty("EXPRESSION") Expression expression,
      @JsonProperty("ALIAS") @Nullable SimpleName alias) {
    this(SqlContextImpl.getNoDbInstance(), expression, alias, null, null);
  }

  @Override
  public <C extends SelectColumn> C transfer(Context<?, ?, C, ?, ?, ?, ?> targetContext,
      @Nullable FromContext fromContext, @Nullable BindMap bindMap) {
    if ((fromContext == null) && (bindMap == null) && targetContext.equals(context)) {
      @SuppressWarnings("unchecked")
      var result = (C) this;
      return result;
    }
    return targetContext.column(expression, alias, fromContext, bindMap);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return expression.getBinds();
  }

  @Override
  public void append(CodeBuilder builder) {
    expression.append(builder);
    if (alias != null) {
      builder.append(' ');
      alias.append(builder);
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
    SqlColumnExpression that = (SqlColumnExpression) o;
    return expression.equals(that.expression)
        && Objects.equals(alias, that.alias)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = expression.hashCode();
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    result = 31 * result + context.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnExpression{"
        + "expression=" + expression
        + ", alias=" + alias
        + ", context=" + context
        + '}';
  }
}
