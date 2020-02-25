package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

class SqlTypeAdapterBigDecimal extends SqlTypeAdapterBase<BigDecimal> {

  private static final SqlTypeAdapterBigDecimal INSTANCE = new SqlTypeAdapterBigDecimal();

  /**
   * @return instance of this type adapter
   */
  static SqlTypeAdapterBigDecimal getInstance() {
    return INSTANCE;
  }

  @Override
  protected BigDecimal readValueInternal(DbResultSet resultSet, int columnIndex)
      throws SQLException {
    return resultSet.getBigDecimal(columnIndex);
  }

  @Override
  protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex,
      BigDecimal value) throws SQLException {
    statement.setBigDecimal(parameterIndex, value);
  }

  @Override
  public Class<BigDecimal> getType() {
    return BigDecimal.class;
  }

  @Override
  public int getSqlType() {
    return Types.NUMERIC;
  }
}
