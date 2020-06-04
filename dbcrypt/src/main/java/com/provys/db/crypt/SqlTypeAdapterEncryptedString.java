package com.provys.db.crypt;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.crypt.DtEncryptedString;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.defaultdb.types.SqlTypeAdapter;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for DtEncryptedString class.
 * Values read from database might be plain or encoded. Values are always bound as plain
 * (unencrypted) value.
 */
@Immutable
public class SqlTypeAdapterEncryptedString implements SqlTypeAdapter<DtEncryptedString> {

  private static final SqlTypeAdapterEncryptedString INSTANCE = new SqlTypeAdapterEncryptedString();
  private static final long serialVersionUID = -5232156662668957595L;

  /**
   * Instance of String type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterEncryptedString getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterEncryptedString() {
  }

  @Override
  public Class<DtEncryptedString> getType() {
    return DtEncryptedString.class;
  }

  @Override
  public int getSqlType() {
    return Types.VARCHAR;
  }

  @Override
  public DtEncryptedString readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return DtEncryptedString.valueOf(resultSet.getNonNullString(columnIndex));
  }

  @Override
  public DtEncryptedString readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return DtEncryptedString.valueOf(resultSet.getNonNullString(columnLabel));
  }

  @Override
  public @Nullable DtEncryptedString readNullableValue(DbResultSet resultSet, int columnIndex) {
    var value = resultSet.getNullableString(columnIndex);
    return (value == null) ? null : DtEncryptedString.valueOf(value);
  }

  @Override
  public @Nullable DtEncryptedString readNullableValue(DbResultSet resultSet, String columnLabel) {
    var value = resultSet.getNullableString(columnLabel);
    return (value == null) ? null : DtEncryptedString.valueOf(value);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable DtEncryptedString value) {
    statement.setNullableString(parameterIndex, (value == null) ? null : value.getValue());
  }

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterDtEncryptedString{}";
  }
}
