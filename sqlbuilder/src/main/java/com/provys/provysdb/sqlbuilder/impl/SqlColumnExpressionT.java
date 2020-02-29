package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.ExpressionT;
import com.provys.provysdb.sqlbuilder.SqlColumnT;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

class SqlColumnExpressionT<T> extends SqlColumnBase implements SqlColumnT<T> {

  private final ExpressionT<T> expression;

  SqlColumnExpressionT(ExpressionT<T> expression, @Nullable SqlIdentifier alias) {
    super(alias);
    this.expression = Objects.requireNonNull(expression);
  }

  @Override
  public SqlColumnT<T> withAlias(SqlIdentifier alias) {
    if (alias.equals(getAlias())) {
      return this;
    }
    return new SqlColumnExpressionT<>(expression, alias);
  }

  @Override
  public void addSql(CodeBuilder builder) {
    expression.addSql(builder);
  }

  @Override
  public Class<T> getType() {
    return expression.getType();
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
    SqlColumnExpressionT<?> that = (SqlColumnExpressionT<?>) o;
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
    return "SqlColumnExpressionT{"
        + "expression=" + expression
        + ", " + super.toString() + '}';
  }
}
