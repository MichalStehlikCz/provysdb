package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

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

  @Override
  public boolean isAssignableFrom(Class<?> sourceType) {
    return (sourceType == Double.class)
        || (sourceType == Byte.class)
        || (sourceType == Short.class)
        || (sourceType == Integer.class)
        || (sourceType == Long.class)
        || (sourceType == Float.class);
  }

  @Override
  public @PolyNull Double convert(@PolyNull Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof Double) {
      return (Double) value;
    }
    if (value instanceof Byte) {
      return (double) (byte) value;
    }
    if (value instanceof Short) {
      return (double) (short) value;
    }
    if (value instanceof Integer) {
      return (double) (int) value;
    }
    if (value instanceof Long) {
      return (double) (long) value;
    }
    if (value instanceof Float) {
      return (double) (float) value;
    }
    throw new InternalException("Conversion not supported from " + value.getClass() + " to Double");
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDouble{}";
  }
}
