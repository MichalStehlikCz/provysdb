package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.SqlException;
import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import java.sql.SQLException;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class SqlTypeAdapterBase<T> implements SqlTypeAdapter<T> {

  @SuppressWarnings("WeakerAccess")
  protected abstract @Nullable T readValueInternal(DbResultSet resultSet, int columnIndex)
      throws SQLException;

  @Override
  public T readNonnullValue(DbResultSet resultSet, int columnIndex) {
    try {
      var value = readValueInternal(resultSet, columnIndex);
      if (resultSet.wasNull() || (value == null)) {
        throw new SqlException("Mandatory column value was null (" + columnIndex + ", " +
            resultSet.getMetaData().getColumnName(columnIndex) + ')');
      }
      return value;
    } catch (SQLException e) {
      throw new SqlException("Exception reading from result set", e);
    }
  }

  @Override
  public T readNonnullValue(DbResultSet resultSet, String columnLabel) {
    try {
      return readNonnullValue(resultSet, resultSet.findColumn(columnLabel));
    } catch (SQLException e) {
      throw new SqlException("Column " + columnLabel + " not found", e);
    }
  }

  @Override
  public @Nullable T readNullableValue(DbResultSet resultSet, int columnIndex) {
    try {
      var value = readValueInternal(resultSet, columnIndex);
      if (resultSet.wasNull()) {
        return null;
      }
      return value;
    } catch (SQLException e) {
      throw new SqlException("Exception reading from result set", e);
    }
  }

  @Override
  public @Nullable T readNullableValue(DbResultSet resultSet, String columnLabel) {
    try {
      return readNullableValue(resultSet, resultSet.findColumn(columnLabel));
    } catch (SQLException e) {
      throw new SqlException("Column " + columnLabel + " not found", e);
    }
  }

  @SuppressWarnings("WeakerAccess")
  protected abstract void bindValueInternal(DbPreparedStatement statement, int parameterIndex,
      T value)
      throws SQLException;

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable T value) {
    try {
      if (value == null) {
        var typeName = getTypeName();
        if (typeName.isPresent()) {
          statement.setNull(parameterIndex, getSqlType(), typeName.get());
        } else {
          statement.setNull(parameterIndex, getSqlType());
        }
      } else {
        bindValueInternal(statement, parameterIndex, value);
      }
    } catch (SQLException e) {
      throw new SqlException(
          "Error binding variable (" + statement + ", " + parameterIndex + ", " + value + ')', e);
    }
  }
}
