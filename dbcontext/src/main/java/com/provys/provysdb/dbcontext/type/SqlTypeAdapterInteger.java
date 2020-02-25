package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import java.sql.SQLException;
import java.sql.Types;

class SqlTypeAdapterInteger extends SqlTypeAdapterBase<Integer> {

  private static final SqlTypeAdapterInteger INSTANCE = new SqlTypeAdapterInteger();

  /**
   * @return instance of this type adapter
   */
  static SqlTypeAdapterInteger getInstance() {
    return INSTANCE;
  }

  @Override
  protected Integer readValueInternal(DbResultSet resultSet, int columnIndex) throws SQLException {
    return resultSet.getInt(columnIndex);
  }

  @Override
  protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex, Integer value)
      throws SQLException {
    statement.setInt(parameterIndex, value);
  }

  @Override
  public Class<Integer> getType() {
    return Integer.class;
  }

  @Override
  public int getSqlType() {
    return Types.INTEGER;
  }
}
