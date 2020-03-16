package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.SqlException;
import com.provys.provysdb.dbsqlbuilder.BindVariableT;
import com.provys.provysdb.sql.BindName;
import sqlbuilder.BindValueBuilder;
import com.provys.provysdb.sql.CodeBuilder;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class BindVariableImpl<T> implements BindVariableT<T> {

  private final BindValueBuilder<T> bindValueBuilder;

  BindVariableImpl(BindValueBuilder<T> bindValueBuilder) {
    this.bindValueBuilder = bindValueBuilder;
  }

  @Override
  public Class<T> getType() {
    return bindValueBuilder.getType();
  }

  @Override
  public @Nullable T getValue() {
    return bindValueBuilder.getValue();
  }

  @Override
  public <U> @Nullable U getValue(Class<U> returnType) {
    return bindValueBuilder.getValue(returnType);
  }

  @Override
  public BindVariableT<T> withValue(@Nullable Object newValue) {
    BindValueBuilder<T> result = bindValueBuilder.withValue(newValue);
    if (result == this) {
      return this;
    }
    if (result instanceof BindVariableT<?>) {
      return (BindVariableT<T>) result;
    }
    return new BindVariableImpl<>(result);
  }

  @Override
  public BindVariableT<T> combine(BindName other) {
    var result = bindValueBuilder.combine(other);
    //noinspection ObjectEquality - intentional
    if (result == this) {
      return this;
    }
    if (result instanceof BindVariableT<?>) {
      return (BindVariableT<T>) result;
    }
    return new BindVariableImpl<>(result);
  }

  @Override
  public String getName() {
    return bindValueBuilder.getName();
  }

  @Override
  public void addSql(CodeBuilder builder) {
    bindValueBuilder.addSql(builder);
  }

  @Override
  public void bind(DbPreparedStatement statement, int parameterIndex) {
    try {
      var value = getValue();
      statement.setNullableValue(parameterIndex, value, getType());
    } catch (Exception e) {
      throw new SqlException("Error binding value " + getValue()
          + " to variable " + getName(), e);
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BindVariableImpl)) {
      return false;
    }
    BindVariableImpl<?> that = (BindVariableImpl<?>) o;
    return Objects.equals(bindValueBuilder, that.bindValueBuilder);
  }

  @Override
  public int hashCode() {
    return bindValueBuilder != null ? bindValueBuilder.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "BindVariableImpl{"
        + "bindValue=" + bindValueBuilder
        + '}';
  }
}
