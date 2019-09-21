package com.provys.provysdb.dbcontext.type;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterBigDecimal extends SqlTypeAdapterBase<BigDecimal> {
    @Nonnull
    @Override
    protected BigDecimal readValueInternal(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getBigDecimal(columnIndex);
    }

    @Override
    protected void bindValueInternal(PreparedStatement statement, int parameterIndex, BigDecimal value) throws SQLException {
        statement.setBigDecimal(parameterIndex, value);
    }

    @Override
    public int getSqlType() {
        return Types.NUMERIC;
    }
}
