package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DbBoolean;
import com.provys.common.datatype.DtBoolean;
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
 * Default type adapter for Boolean class; translates boolean to 'Y' / 'N' character.
 */
@Immutable
public class SqlTypeAdapterBoolean implements SqlTypeAdapter<Boolean> {

  private static final SqlTypeAdapterBoolean INSTANCE = new SqlTypeAdapterBoolean();
  private static final long serialVersionUID = 2845824887138807807L;

  /**
   * Instance of boolean type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterBoolean getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterBoolean() {
  }

  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  @Override
  public int getSqlType() {
    return Types.CHAR;
  }


  @Override
  public Boolean readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullBoolean(columnIndex);
  }

  @Override
  public Boolean readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullBoolean(columnLabel);
  }

  @Override
  public @Nullable Boolean readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableBoolean(columnIndex);
  }

  @Override
  public @Nullable Boolean readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableBoolean(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable Boolean value) {
    statement.setNullableBoolean(parameterIndex, value);
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBoolean{}";
  }
}
