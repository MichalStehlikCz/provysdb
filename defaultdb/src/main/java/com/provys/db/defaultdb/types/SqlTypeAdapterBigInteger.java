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
 * Default type adapter for BigInteger class.
 */
@Immutable
public class SqlTypeAdapterBigInteger implements SqlTypeAdapter<BigInteger> {

  private static final SqlTypeAdapterBigInteger INSTANCE = new SqlTypeAdapterBigInteger();
  private static final long serialVersionUID = 3450037150697240635L;

  /**
   * Instance of BigInteger type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterBigInteger getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterBigInteger() {
  }

  @Override
  public Class<BigInteger> getType() {
    return BigInteger.class;
  }

  @Override
  public int getSqlType() {
    return Types.BIGINT;
  }

  @Override
  public BigInteger readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullBigInteger(columnIndex);
  }

  @Override
  public BigInteger readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullBigInteger(columnLabel);
  }

  @Override
  public @Nullable BigInteger readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableBigInteger(columnIndex);
  }

  @Override
  public @Nullable BigInteger readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableBigInteger(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable BigInteger value) {
    statement.setNullableBigInteger(parameterIndex, value);
  }

  @Override
  public boolean isAssignableFrom(Class<?> sourceType) {
    return (sourceType == BigInteger.class)
        || (sourceType == Byte.class)
        || (sourceType == Short.class)
        || (sourceType == Integer.class)
        || (sourceType == Long.class);
  }

  @Override
  public @PolyNull BigInteger convert(@PolyNull Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof BigInteger) {
      return (BigInteger) value;
    }
    if (value instanceof Byte) {
      return BigInteger.valueOf((byte) value);
    }
    if (value instanceof Short) {
      return BigInteger.valueOf((short) value);
    }
    if (value instanceof Integer) {
      return BigInteger.valueOf((int) value);
    }
    if (value instanceof Long) {
      return BigInteger.valueOf((long) value);
    }
    throw new InternalException(
        "Conversion not supported from " + value.getClass() + " to BigInteger");
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBigInteger{}";
  }
}
