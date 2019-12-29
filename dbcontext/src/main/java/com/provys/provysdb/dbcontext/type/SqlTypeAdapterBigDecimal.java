package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterBigDecimal extends SqlTypeAdapterBase<BigDecimal> {

    private static final SqlTypeAdapterBigDecimal INSTANCE = new SqlTypeAdapterBigDecimal();

    /**
     * @return instance of this type adapter
     */
    public static SqlTypeAdapterBigDecimal getInstance() {
        return INSTANCE;
    }

    @Nonnull
    @Override
    protected BigDecimal readValueInternal(DbResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getBigDecimal(columnIndex);
    }

    @Override
    protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex, BigDecimal value) throws SQLException {
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
