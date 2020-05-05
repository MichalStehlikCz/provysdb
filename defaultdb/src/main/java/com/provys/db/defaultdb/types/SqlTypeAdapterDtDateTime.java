package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

/**
 * Default type adapter for DtDateTime class.
 */
@Immutable
public class SqlTypeAdapterDtDateTime implements SqlTypeAdapter<DtDateTime> {

  private static final SqlTypeAdapterDtDateTime INSTANCE = new SqlTypeAdapterDtDateTime();
  private static final long serialVersionUID = 1233606164740718260L;

  /**
   * Instance of DtDateTime type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterDtDateTime getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterDtDateTime() {
  }

  @Override
  public Class<DtDateTime> getType() {
    return DtDateTime.class;
  }

  @Override
  public int getSqlType() {
    return Types.TIMESTAMP;
  }

  @Override
  public DtDateTime readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullDtDateTime(columnIndex);
  }

  @Override
  public DtDateTime readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullDtDateTime(columnLabel);
  }

  @Override
  public @Nullable DtDateTime readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableDtDateTime(columnIndex);
  }

  @Override
  public @Nullable DtDateTime readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableDtDateTime(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable DtDateTime value) {
    statement.setNullableDtDateTime(parameterIndex, value);
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDtDateTime{}";
  }
}
