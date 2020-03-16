package com.provys.db.sqldb.types;

import com.provys.common.datatype.DtBoolean;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for Boolean class; translates boolean to 'Y' / 'N' character.
 */
public class SqlTypeAdapterBoolean implements SqlTypeAdapter<Boolean> {

  private static final String LITERAL_TRUE = '\'' + DtBoolean.toProvysDb(true) + '\'';
  private static final String LITERAL_FALSE = '\'' + DtBoolean.toProvysDb(false) + '\'';

  private static final SqlTypeAdapterBoolean INSTANCE = new SqlTypeAdapterBoolean();

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

  @Override
  public String getLiteral(@Nullable Boolean value) {
    if (value == null) {
      return "NULL";
    }
    return value ? LITERAL_TRUE : LITERAL_FALSE;
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBoolean{}";
  }
}
