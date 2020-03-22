package com.provys.db.sqldb.types;

import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for Integer class.
 */
public class SqlTypeAdapterInteger implements SqlTypeAdapter<Integer> {

  private static final SqlTypeAdapterInteger INSTANCE = new SqlTypeAdapterInteger();

  /**
   * Instance of Integer type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterInteger getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterInteger() {
  }

  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }

  @Override
  public String getName() {
    return "INTEGER";
  }

  @Override
  public int getSqlType() {
    return Types.INTEGER;
  }

  @Override
  public Integer readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullInteger(columnIndex);
  }

  @Override
  public Integer readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullInteger(columnLabel);
  }

  @Override
  public @Nullable Integer readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableInteger(columnIndex);
  }

  @Override
  public @Nullable Integer readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableInteger(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable Integer value) {
    statement.setNullableInteger(parameterIndex, value);
  }

  @Override
  public String getLiteral(@Nullable Integer value) {
    return (value == null) ? "NULL" : value.toString();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterInteger{}";
  }
}
