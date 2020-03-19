package com.provys.db.sqldb.types;

import com.provys.common.datatype.DtDateTime;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for DtDateTime class.
 */
public class SqlTypeAdapterDtDateTime implements SqlTypeAdapter<DtDateTime> {

  private static final SqlTypeAdapterDtDateTime INSTANCE = new SqlTypeAdapterDtDateTime();

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

  @Override
  public String getLiteral(@Nullable DtDateTime value) {
    if (value == null) {
      return "NULL";
    }
    return "TO_DATE('" + value.toIso() + "', 'YYYY-MM-DD\"T\"HH24:MI:SS')";
  }

  @Override
  public void appendLiteral(StringBuilder builder, @Nullable DtDateTime value) {
    if (value == null) {
      builder.append("NULL");
    } else {
      builder.append("TO_DATE('").append(value.toIso()).append("', 'YYYY-MM-DD\"T\"HH24:MI:SS')");
    }
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDtDateTime{}";
  }
}
