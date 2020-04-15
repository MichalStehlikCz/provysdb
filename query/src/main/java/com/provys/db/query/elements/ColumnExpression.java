package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.SimpleName;
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
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SelectColumn
@Immutable
final class ColumnExpression<T> implements SelectColumn<T> {

  @JsonProperty("EXPRESSION")
  private final Expression<T> expression;
  @JsonProperty("ALIAS")
  private final @Nullable SimpleName alias;

  ColumnExpression(Expression<T> expression, @Nullable SimpleName alias,
      @Nullable BindMap bindMap) {
    this.expression = (bindMap == null) ? expression : expression.mapBinds(bindMap);
    this.alias = alias;
  }

  @JsonCreator
  ColumnExpression(@JsonProperty("EXPRESSION") Expression<T> expression,
      @JsonProperty("ALIAS") @Nullable SimpleName alias) {
    this(expression, alias, null);
  }

  /**
   * Value of field expression.
   *
   * @return value of field expression
   */
  public Expression<T> getExpression() {
    return expression;
  }

  @Override
  public @Nullable SimpleName getAlias() {
    return alias;
  }

  @Override
  public Class<T> getType() {
    return expression.getType();
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return expression.getBinds();
  }

  @Override
  public SelectColumn<T> mapBinds(BindMap bindMap) {
    var newExpression = expression.mapBinds(bindMap);
    //noinspection ObjectEquality - if expression doesn't do deduplication, neither will we
    if (newExpression == expression) {
      return this;
    }
    return new ColumnExpression<>(newExpression, alias);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.selectColumn(expression, alias);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnExpression<?> that = (ColumnExpression<?>) o;
    return expression.equals(that.expression)
        && Objects.equals(alias, that.alias);
  }

  @Override
  public int hashCode() {
    int result = expression.hashCode();
    result = 31 * result + (alias != null ? alias.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ColumnExpression{"
        + "expression=" + expression
        + ", alias=" + alias
        + '}';
  }
}
