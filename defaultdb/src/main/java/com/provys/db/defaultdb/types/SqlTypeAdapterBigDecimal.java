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
 * Default type adapter for BigDecimal class.
 */
@Immutable
public class SqlTypeAdapterBigDecimal implements SqlTypeAdapter<BigDecimal> {

  private static final SqlTypeAdapterBigDecimal INSTANCE = new SqlTypeAdapterBigDecimal();
  private static final long serialVersionUID = -1990683305372391066L;

  /**
   * Instance of BigDecimal type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterBigDecimal getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterBigDecimal() {
  }

  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  @Override
  public int getSqlType() {
    return Types.NUMERIC;
  }

  @Override
  public BigDecimal readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullBigDecimal(columnIndex);
  }

  @Override
  public BigDecimal readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullBigDecimal(columnLabel);
  }

  @Override
  public @Nullable BigDecimal readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableBigDecimal(columnIndex);
  }

  @Override
  public @Nullable BigDecimal readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableBigDecimal(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable BigDecimal value) {
    statement.setNullableBigDecimal(parameterIndex, value);
  }

  @Override
  public boolean isAssignableFrom(Class<?> sourceType) {
    return (sourceType == BigDecimal.class)
        || (sourceType == Byte.class)
        || (sourceType == Short.class)
        || (sourceType == Integer.class)
        || (sourceType == Long.class)
        || (sourceType == Float.class)
        || (sourceType == Double.class)
        || (sourceType == BigInteger.class);
  }

  @Override
  public @PolyNull BigDecimal convert(@PolyNull Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof BigDecimal) {
      return (BigDecimal) value;
    }
    if (value instanceof Byte) {
      return BigDecimal.valueOf((byte) value);
    }
    if (value instanceof Short) {
      return BigDecimal.valueOf((short) value);
    }
    if (value instanceof Integer) {
      return BigDecimal.valueOf((int) value);
    }
    if (value instanceof Long) {
      return BigDecimal.valueOf((long) value);
    }
    if (value instanceof Float) {
      return BigDecimal.valueOf((float) value);
    }
    if (value instanceof Double) {
      return BigDecimal.valueOf((double) value);
    }
    if (value instanceof BigInteger) {
      return new BigDecimal((BigInteger) value);
    }
    throw new InternalException(
        "Conversion not supported from " + value.getClass() + " to BigDecimal");
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBigDecimal{}";
  }
}
