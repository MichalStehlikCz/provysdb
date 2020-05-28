package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.datatype.DtBinaryData;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for DtBinaryData class.
 */
@Immutable
public class SqlTypeAdapterDtBinaryData implements SqlTypeAdapter<DtBinaryData> {

  private static final SqlTypeAdapterDtBinaryData INSTANCE = new SqlTypeAdapterDtBinaryData();
  private static final long serialVersionUID = 734993579636394619L;

  /**
   * Instance of DtBinaryData type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterDtBinaryData getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterDtBinaryData() {
  }

  @Override
  public Class<DtBinaryData> getType() {
    return DtBinaryData.class;
  }

  @Override
  public int getSqlType() {
    return Types.BLOB;
  }

  @Override
  public DtBinaryData readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullDtBinaryData(columnIndex);
  }

  @Override
  public DtBinaryData readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullDtBinaryData(columnLabel);
  }

  @Override
  public @Nullable DtBinaryData readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableDtBinaryData(columnIndex);
  }

  @Override
  public @Nullable DtBinaryData readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableDtBinaryData(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable DtBinaryData value) {
    statement.setNullableDtBinaryData(parameterIndex, value);
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDtBinaryData{}";
  }
}