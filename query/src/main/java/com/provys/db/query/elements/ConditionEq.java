package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.common.datatype.DbBoolean;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("EQ")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlFromClause
final class ConditionEq<T> implements Condition {

  @JsonProperty("EXPR1")
  private final Expression<T> expression1;
  @JsonProperty("EXPR2")
  private final Expression<T> expression2;

  @JsonCreator
  ConditionEq(@JsonProperty("EXPR1") Expression<T> expression1,
      @JsonProperty("EXPR2") Expression<T> expression2) {
    this.expression1 = expression1;
    this.expression2 = expression2;
  }

  /**
   * Value of field expression1.
   *
   * @return value of field expression1
   */
  public Expression<T> getExpression1() {
    return expression1;
  }

  /**
   * Value of field expression2.
   *
   * @return value of field expression2
   */
  public Expression<T> getExpression2() {
    return expression2;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return new BindVariableCollector()
        .add(expression1)
        .add(expression2)
        .getBinds();
  }

  @Override
  public Condition mapBinds(BindMap bindMap) {
    var newExpression1 = expression1.mapBinds(bindMap);
    var newExpression2 = expression2.mapBinds(bindMap);
    if (newExpression1.equals(expression1) && newExpression2.equals(expression2)) {
      return this;
    }
    return new ConditionEq<>(newExpression1, newExpression2);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.eq(expression1, expression2);
  }

  @Override
  public Class<DbBoolean> getType() {
    return DbBoolean.class;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionEq<?> that = (ConditionEq<?>) o;
    return expression1.equals(that.expression1)
        && expression2.equals(that.expression2);
  }

  @Override
  public int hashCode() {
    int result = expression1.hashCode();
    result = 31 * result + expression2.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ConditionEq{"
        + "expression1=" + expression1
        + ", expression2=" + expression2
        + '}';
  }
}
