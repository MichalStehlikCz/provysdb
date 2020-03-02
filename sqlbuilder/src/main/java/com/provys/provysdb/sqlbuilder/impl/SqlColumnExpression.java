package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Expression;
import com.provys.provysdb.sqlbuilder.SqlColumn;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class SqlColumnExpression extends SqlColumnBase {

  private final Expression expression;

  SqlColumnExpression(Expression expression, @Nullable SqlIdentifier alias) {
    super(alias);
    this.expression = Objects.requireNonNull(expression);
  }

  @Override
  public SqlColumn withAlias(SqlIdentifier alias) {
    if (alias.equals(getAlias())) {
      return this;
    }
    return new SqlColumnExpression(expression, alias);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    expression.addSql(builder);
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
    SqlColumnExpression that = (SqlColumnExpression) o;
    return Objects.equals(expression, that.expression);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (expression != null ? expression.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlColumnExpression{"
        + "expression=" + expression
        + ", " + super.toString() + '}';
  }
}
