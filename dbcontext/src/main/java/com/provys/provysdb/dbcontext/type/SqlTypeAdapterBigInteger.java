package com.provys.provysdb.dbcontext.type;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterBigInteger extends SqlTypeAdapterBase<BigInteger> {

    private static final Logger LOG = LogManager.getLogger(SqlTypeAdapterBigInteger.class);

    private static final SqlTypeAdapterBigInteger INSTANCE = new SqlTypeAdapterBigInteger();

    /**
     * @return instance of this type adapter
     */
    public static SqlTypeAdapterBigInteger getInstance() {
        return INSTANCE;
    }

    @Nonnull
    @Override
    protected BigInteger readValueInternal(DbResultSet resultSet, int columnIndex) throws SQLException {
        var value = resultSet.getBigDecimal(columnIndex);
        if (resultSet.wasNull()) {
            return BigInteger.ZERO;
        }
        try {
            return value.toBigIntegerExact();
        } catch (ArithmeticException e) {
            throw new InternalException(LOG, "Fractional part encountered when reading BigInteger, column index " +
                    columnIndex);
        }
    }

    @Override
    protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex, BigInteger value) throws SQLException {
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
