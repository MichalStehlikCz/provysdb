package com.provys.provysdb.dbsqlbuilder.impl;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.SqlException;
import com.provys.provysdb.dbsqlbuilder.BindVariableT;
import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.BindValue;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

final class BindVariableImpl<T> implements BindVariableT<T> {

  private final BindValue<T> bindValue;

  BindVariableImpl(BindValue<T> bindValue) {
    this.bindValue = bindValue;
  }

  @Override
  public Class<T> getType() {
    return bindValue.getType();
  }

  @Override
  public @Nullable T getValue() {
    return bindValue.getValue();
  }

  @Override
  public <U> @Nullable U getValue(Class<U> returnType) {
    return bindValue.getValue(returnType);
  }

  @Override
  public BindVariableT<T> withValue(@Nullable Object newValue) {
    BindValue<T> result = bindValue.withValue(newValue);
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
    var result = bindValue.combine(other);
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
    return bindValue.getName();
  }

  @Override
  public void addSql(CodeBuilder builder) {
    bindValue.addSql(builder);
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
    return Objects.equals(bindValue, that.bindValue);
  }

  @Override
  public int hashCode() {
    return bindValue != null ? bindValue.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "BindVariableImpl{"
        + "bindValue=" + bindValue
        + '}';
  }
}
