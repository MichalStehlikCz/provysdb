package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.NonNull;
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
@JsonSerialize(using = LiteralSerializer.class)
@JsonDeserialize(using = LiteralDeserializer.class)
public final class Literal<T> implements Expression<T> {

  private final @NonNull T value;

  public Literal(@NonNull T value) {
    this.value = value;
  }

  public @NonNull T getValue() {
    return value;
  }

  @Override
  public Class<T> getType() {
    @SuppressWarnings("unchecked") // we know that class of object has correct type parameter
    var result = (Class<T>) value.getClass();
    return result;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public Expression<T> mapBinds(BindMap bindMap) {
    return this;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Literal<?> literal = (Literal<?>) o;
    return value.equals(literal.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "Literal{"
        + "value=" + value
        + '}';
  }
}
