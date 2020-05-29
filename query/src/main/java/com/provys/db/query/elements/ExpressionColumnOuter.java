package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Expression based on column or property from one of the sources.
 *
 * @param <T> is type of value expression yields
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("COLUMNOUTER")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from Expression
@Immutable
final class ExpressionColumnOuter<T> implements Expression<T> {

  @JsonUnwrapped
  private final ExpressionColumn<T> column;

  @JsonCreator
  ExpressionColumnOuter(@JsonUnwrapped ExpressionColumn<T> column) {
    this.column = column;
  }

  @Override
  public Class<T> getType() {
    return column.getType();
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return column.getBinds();
  }

  @Override
  public Expression<T> mapBinds(BindMap bindMap) {
    //noinspection ObjectEquality, ConstantConditions
    assert column.mapBinds(bindMap) != column;
    return this;
  }

  @Override
  public void apply(ExpressionConsumer consumer) {
    consumer.columnOuter(column.getType(), column.getTable(), column.getColumn());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExpressionColumnOuter<?> that = (ExpressionColumnOuter<?>) o;
    return column.equals(that.column);
  }

  @Override
  public int hashCode() {
    return column.hashCode();
  }

  @Override
  public String toString() {
    return "ExpressionColumnOuter{"
        + "column=" + column
        + '}';
  }
}
