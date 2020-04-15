package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.math.BigInteger;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

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

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBigInteger{}";
  }
}
