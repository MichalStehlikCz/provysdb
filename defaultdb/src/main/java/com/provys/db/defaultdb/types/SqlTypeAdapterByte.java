package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for Byte class.
 */
@Immutable
public class SqlTypeAdapterByte implements SqlTypeAdapter<Byte> {

  private static final SqlTypeAdapterByte INSTANCE = new SqlTypeAdapterByte();
  private static final long serialVersionUID = -2402498755542208902L;

  /**
   * Instance of Byte type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterByte getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterByte() {
  }

  @Override
  public Class<Byte> getType() {
    return Byte.class;
  }

  @Override
  public int getSqlType() {
    return Types.TINYINT;
  }

  @Override
  public Byte readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullByte(columnIndex);
  }

  @Override
  public Byte readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullByte(columnLabel);
  }

  @Override
  public @Nullable Byte readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableByte(columnIndex);
  }

  @Override
  public @Nullable Byte readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableByte(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable Byte value) {
    statement.setNullableByte(parameterIndex, value);
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterByte{}";
  }
}
