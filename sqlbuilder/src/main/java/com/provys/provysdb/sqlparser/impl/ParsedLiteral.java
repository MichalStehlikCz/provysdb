package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.LiteralT;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

class ParsedLiteral<T> extends ParsedTokenBase implements LiteralT<T> {

  private final LiteralT<T> value;

  ParsedLiteral(int line, int pos, LiteralT<T> value) {
    super(line, pos);
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public T getValue() {
    return value.getValue();
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.LITERAL;
  }

  @Override
  public SpaceMode spaceBefore() {
    return SpaceMode.NORMAL;
  }

  @Override
  public SpaceMode spaceAfter() {
    return SpaceMode.NORMAL;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    value.addSql(builder);
  }

  @Override
  public Class<T> getType() {
    return value.getType();
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
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedLiteral{"
        + "value=" + value
        + ", " + super.toString() + '}';
  }
}
