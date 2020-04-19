package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

/**
 * Default type adapter for DtDate class.
 */
@Immutable
public class SqlTypeAdapterDtDate implements SqlTypeAdapter<DtDate> {

  private static final SqlTypeAdapterDtDate INSTANCE = new SqlTypeAdapterDtDate();
  private static final long serialVersionUID = -6338012492787869489L;

  /**
   * Instance of DtDate type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterDtDate getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterDtDate() {
  }

  @Override
  public Class<DtDate> getType() {
    return DtDate.class;
  }

  @Override
  public int getSqlType() {
    return Types.DATE;
  }

  @Override
  public DtDate readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullDtDate(columnIndex);
  }

  @Override
  public DtDate readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullDtDate(columnLabel);
  }

  @Override
  public @Nullable DtDate readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableDtDate(columnIndex);
  }

  @Override
  public @Nullable DtDate readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableDtDate(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable DtDate value) {
    statement.setNullableDtDate(parameterIndex, value);
  }

  @Override
  public boolean isAssignableFrom(Class<?> sourceType) {
    return (sourceType == DtDate.class)
        || (sourceType == LocalDate.class);
  }

  @Override
  public @PolyNull DtDate convert(@PolyNull Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof DtDate) {
      return (DtDate) value;
    }
    if (value instanceof LocalDate) {
      return DtDate.ofLocalDate((LocalDate) value);
    }
    throw new InternalException("Conversion not supported from " + value.getClass() + " to DtDate");
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDtDate{}";
  }
}
