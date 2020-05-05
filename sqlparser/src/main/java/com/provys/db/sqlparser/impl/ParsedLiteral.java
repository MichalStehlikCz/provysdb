package com.provys.db.sqlparser.impl;

import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.ImmutableTypeParameter;
import com.provys.db.query.elements.ElementFactory;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
final class ParsedLiteral<@ImmutableTypeParameter T extends Serializable> extends ParsedTokenBase {

  private final Expression<T> value;

  ParsedLiteral(int line, int pos, Expression<T> value) {
    super(line, pos);
    this.value = Objects.requireNonNull(value);
  }

  ParsedLiteral(int line, int pos, Class<T> type, T value) {
    this(line, pos, ElementFactory.getInstance().literal(type, value));
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.LITERAL;
  }

  Class<T> getType() {
    return value.getType();
  }

  /**
   * Value of field value.
   *
   * @return value of field value
   */
  Expression<T> getValue() {
    return value;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return value.getBinds();
  }

  @Override
  public SqlToken mapBinds(BindMap bindMap) {
    var newValue = value.mapBinds(bindMap);
    if (newValue.equals(value)) {
      return this;
    }
    return new ParsedLiteral<>(getLine(), getPos(), newValue);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    value.apply(consumer);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ParsedLiteral<?> that = (ParsedLiteral<?>) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedLiteral{"
        + "value=" + value
        + ", " + super.toString() + '}';
  }
}
