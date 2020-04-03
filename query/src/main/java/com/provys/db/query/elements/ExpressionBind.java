package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents expression, built on bind variable. Value of expression is that of bind variable.
 *
 * @param <T> is type expression evaluates to
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("BIND")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlExpression
public final class ExpressionBind<T> implements Expression<T> {

  /**
   * Create expression for supplied bind variable. Type of expression is unknown at compile time, as
   * bind variables are not generic.
   *
   * @param bindVariable is bind variable expression will be based on. Expression will accept type
   *                     from this variable
   * @return new bind expression based on supplied variable
   */
  @JsonCreator
  public static ExpressionBind<?> ofBindVariable(@JsonUnwrapped BindVariable bindVariable) {
    return new ExpressionBind<>(bindVariable.getType(), bindVariable);
  }

  /**
   * Type of bind expression. It can be evaluated from bind variable and thus is not serialized, but
   * we still prefer to have variable here to make compile time type checking smoother
   */
  private final Class<T> type;
  @JsonUnwrapped
  private final BindVariable bindVariable;

  private static void verifyType(Class<?> type, BindVariable bindVariable) {
    if (!type.isAssignableFrom(bindVariable.getType())) {
      throw new InternalException("Cannot use bind variable " + bindVariable
          + " in expression of type " + type);
    }
  }

  /**
   * Create bind expression of specified type, based on given bind variable. Constructor verifies
   * that bind variable is compatible with required type.
   *
   * @param type         is required type of expression
   * @param bindVariable is bind variable this expression represents
   */
  public ExpressionBind(Class<T> type, BindVariable bindVariable) {
    verifyType(type, bindVariable);
    this.type = type;
    this.bindVariable = bindVariable;
  }

  /**
   * Value of field bindVariable.
   *
   * @return value of field bindVariable
   */
  public BindVariable getBindVariable() {
    return bindVariable;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return List.of(bindVariable);
  }

  @Override
  public Expression<T> mapBinds(BindMap bindMap) {
    BindVariable newBindVariable = bindMap.get(bindVariable.getName());
    if (newBindVariable.equals(bindVariable)) {
      return this;
    }
    return new ExpressionBind<>(type, newBindVariable);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExpressionBind<?> that = (ExpressionBind<?>) o;
    return bindVariable.equals(that.bindVariable);
  }

  @Override
  public int hashCode() {
    return bindVariable.hashCode();
  }

  @Override
  public String toString() {
    return "ExpressionBind{"
        + "type=" + type
        + ", bindVariable=" + bindVariable
        + '}';
  }
}
