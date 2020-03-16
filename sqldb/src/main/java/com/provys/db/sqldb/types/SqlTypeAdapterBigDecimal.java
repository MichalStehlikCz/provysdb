package com.provys.db.sqldb.types;

import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import com.provys.db.dbcontext.SqlTypeAdapter;
import java.math.BigDecimal;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for BigDecimal class.
 */
public class SqlTypeAdapterBigDecimal implements SqlTypeAdapter<BigDecimal> {

  private static final SqlTypeAdapterBigDecimal INSTANCE = new SqlTypeAdapterBigDecimal();

  /**
   * Instance of BigDecimal type adapter.
   *
   * @return instance of this type adapter
   */
  public static SqlTypeAdapterBigDecimal getInstance() {
    return INSTANCE;
  }

  /**
   * Constructor is published to allow subclassing with potential redefinition of selected
   * properties of this type adapter.
   */
  protected SqlTypeAdapterBigDecimal() {
  }

  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  @Override
  public int getSqlType() {
    return Types.NUMERIC;
  }

  @Override
  public BigDecimal readNonNullValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNonNullBigDecimal(columnIndex);
  }

  @Override
  public BigDecimal readNonNullValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNonNullBigDecimal(columnLabel);
  }

  @Override
  public @Nullable BigDecimal readNullableValue(DbResultSet resultSet, int columnIndex) {
    return resultSet.getNullableBigDecimal(columnIndex);
  }

  @Override
  public @Nullable BigDecimal readNullableValue(DbResultSet resultSet, String columnLabel) {
    return resultSet.getNullableBigDecimal(columnLabel);
  }

  @Override
  public void bindValue(DbPreparedStatement statement, int parameterIndex,
      @Nullable BigDecimal value) {
    statement.setNullableBigDecimal(parameterIndex, value);
  }

  @Override
  public String getLiteral(@Nullable BigDecimal value) {
    if (value == null) {
      return "NULL";
    }
    return value.toPlainString();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBigDecimal{}";
  }
}
