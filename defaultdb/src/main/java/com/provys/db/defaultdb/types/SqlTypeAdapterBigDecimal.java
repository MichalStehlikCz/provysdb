package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.math.BigDecimal;
import java.sql.Types;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default type adapter for BigDecimal class.
 */
@Immutable
public class SqlTypeAdapterBigDecimal implements SqlTypeAdapter<BigDecimal> {

  private static final SqlTypeAdapterBigDecimal INSTANCE = new SqlTypeAdapterBigDecimal();
  private static final long serialVersionUID = -1990683305372391066L;

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

  protected Object readResolve() {
    return getInstance();
  }

  @Override
  public String toString() {
    return "SqlTypeAdapterBigDecimal{}";
  }
}
