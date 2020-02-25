package com.provys.provysdb.dbcontext.type;

import com.provys.common.datatype.DtUid;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

class SqlTypeAdapterDtUid implements SqlTypeAdapter<DtUid> {

  private static final SqlTypeAdapterDtUid INSTANCE = new SqlTypeAdapterDtUid();

  /**
   * @return instance of this type adapter
   */
  static SqlTypeAdapterDtUid getInstance() {
    return INSTANCE;
  }

  @Override
  public Class<DtUid> getType() {
    return DtUid.class;
  }

  @Override
  public int getSqlType() {
    return Types.BIGINT;
  }

  @Override
  public DtUid readNonnullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonnullDtUid(columnIndex);
  }

  @Override
  public DtUid readNonnullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonnullDtUid(columnLabel);
  }

  @Override
  public @Nullable DtUid readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableDtUid(columnIndex);
  }

  @Override
  public @Nullable DtUid readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableDtUid(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable DtUid value) {
    statement.setNullableDtUid(parameterIndex, value);
  }
}