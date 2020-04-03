package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
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
public final class LiteralNVarchar implements Expression<String> {

  @JsonProperty("VALUE")
  private final String value;

  @JsonCreator
  public LiteralNVarchar(@JsonProperty("VALUE") String value) {
    this.value = value;
  }

  /**
   * Value of field value.
   *
   * @return value of field value
   */
  public String getValue() {
    return value;
  }

  @Override
  public Class<String> getType() {
    return String.class;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public Expression<String> mapBinds(BindMap bindMap) {
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
    LiteralNVarchar that = (LiteralNVarchar) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "SqlLiteralNVarchar{"
        + "value='" + value + '\''
        + '}';
  }
}
