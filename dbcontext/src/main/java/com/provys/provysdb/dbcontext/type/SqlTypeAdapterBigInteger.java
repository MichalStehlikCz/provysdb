package com.provys.provysdb.dbcontext.type;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Types;

class SqlTypeAdapterBigInteger extends SqlTypeAdapterBase<BigInteger> {

  private static final SqlTypeAdapterBigInteger INSTANCE = new SqlTypeAdapterBigInteger();

  /**
   * Instance of BigInteger type adapter.
   *
   * @return instance of this type adapter
   */
  static SqlTypeAdapterBigInteger getInstance() {
    return INSTANCE;
  }

  @Override
  protected BigInteger readValueInternal(DbResultSet resultSet, int columnIndex)
      throws SQLException {
    var value = resultSet.getBigDecimal(columnIndex);
    if (resultSet.wasNull()) {
      return BigInteger.ZERO;
    }
    castNonNull(value); // safe after wasNull
    try {
      return value.toBigIntegerExact();
    } catch (ArithmeticException e) {
      throw new InternalException(
          "Fractional part encountered when reading BigInteger, column index "
              + columnIndex, e);
    }
  }

  @Override
  protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex,
      BigInteger value) throws SQLException {
    statement.setBigDecimal(parameterIndex, new BigDecimal(value, 0));
  }

  @Override
  public Class<BigInteger> getType() {
    return BigInteger.class;
  }

  @Override
  public int getSqlType() {
    return Types.BIGINT;
  }
}
