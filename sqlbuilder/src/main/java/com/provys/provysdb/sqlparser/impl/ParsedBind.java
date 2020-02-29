package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlFactory;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

class ParsedBind extends ParsedTokenBase implements BindName {

  private final BindName name;

  ParsedBind(int line, int pos, String name) {
    super(line, pos);
    this.name = SqlFactory.bind(name);
  }

  @Override
  public String getName() {
    return name.getName();
  }

  @Override
  public BindName combine(BindName other) {
    return name.combine(other);
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.BIND;
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
    name.addSql(builder);
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
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedBind{"
        + "name=" + name
        + ", " + super.toString() + '}';
  }
}
