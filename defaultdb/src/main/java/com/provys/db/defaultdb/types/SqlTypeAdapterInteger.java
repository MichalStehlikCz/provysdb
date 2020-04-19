package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

/**
 * Default type adapter for Integer class.
 */
@Immutable
public class SqlTypeAdapterInteger implements SqlTypeAdapter<Integer> {

  private static final SqlTypeAdapterInteger INSTANCE = new SqlTypeAdapterInteger();
  private static final long serialVersionUID = 1756354633001560592L;

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
  public boolean isAssignableFrom(Class<?> sourceType) {
    return (sourceType == Integer.class)
        || (sourceType == Byte.class)
        || (sourceType == Short.class);
  }

  @Override
  public @PolyNull Integer convert(@PolyNull Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof Integer) {
      return (Integer) value;
    }
    if (value instanceof Byte) {
      return (int) (byte) value;
    }
    if (value instanceof Short) {
      return (int) (short) value;
    }
    throw new InternalException(
        "Conversion not supported from " + value.getClass() + " to Integer");
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterInteger{}";
  }
}
