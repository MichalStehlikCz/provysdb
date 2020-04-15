package com.provys.db.sqlparser.impl;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.elements.QueryConsumer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.sqlparser.SqlToken;
import com.provys.db.sqlparser.SqlTokenType;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

@Immutable
final class ParsedBind extends ParsedTokenBase {

  private final BindVariable bindVariable;

  ParsedBind(int line, int pos, BindVariable bindVariable) {
    super(line, pos);
    this.bindVariable = bindVariable;
  }

  ParsedBind(int line, int pos, String name) {
    this(line, pos, new BindVariable(name));
  }

  BindVariable getBindVariable() {
    return bindVariable;
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.BIND;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return List.of(bindVariable);
  }

  @Override
  public SqlToken mapBinds(BindMap bindMap) {
    return new ParsedBind(getLine(), getPos(), bindMap.get(bindVariable.getName()));
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.bind(bindVariable.getType(), bindVariable);
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
    ParsedBind that = (ParsedBind) o;
    return bindVariable.equals(that.bindVariable);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + bindVariable.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ParsedBind{"
        + "bindVariable=" + bindVariable
        + ", " + super.toString() + '}';
  }
}
