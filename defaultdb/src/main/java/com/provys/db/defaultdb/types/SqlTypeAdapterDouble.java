package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for Double class.
 */
@Immutable
public class SqlTypeAdapterDouble implements SqlTypeAdapter<Double> {

  private static final SqlTypeAdapterDouble INSTANCE = new SqlTypeAdapterDouble();
  private static final long serialVersionUID = -7289950263321964912L;

  /**
   * Instance of Double type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterDouble getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterDouble() {
  }

  @Override
  public Class<Double> getType() {
    return Double.class;
  }

  @Override
  public int getSqlType() {
    return Types.DOUBLE;
  }

  @Override
  public Double readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullDouble(columnIndex);
  }

  @Override
  public Double readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullDouble(columnLabel);
  }

  @Override
  public @Nullable Double readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableDouble(columnIndex);
  }

  @Override
  public @Nullable Double readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableDouble(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable Double value) {
    statement.setNullableDouble(parameterIndex, value);
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDouble{}";
  }
}
