package com.provys.db.sqldb.types;

import com.provys.common.datatype.DtUid;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for DtUid class.
 */
public class SqlTypeAdapterDtUid implements SqlTypeAdapter<DtUid> {

  private static final SqlTypeAdapterDtUid INSTANCE = new SqlTypeAdapterDtUid();

  /**
   * Instance of DtUid type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterDtUid getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterDtUid() {
  }

  @Override
  public Class<DtUid> getType() {
    return DtUid.class;
  }

  @Override
  public String getName() {
    return "UID";
  }

  @Override
  public int getSqlType() {
    return Types.BIGINT;
  }

  @Override
  public DtUid readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullDtUid(columnIndex);
  }

  @Override
  public DtUid readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullDtUid(columnLabel);
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

  @Override
  public String getLiteral(@Nullable DtUid value) {
    if (value == null) {
      return "NULL";
    }
    return value.getValue().toString();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDtUid{}";
  }
}