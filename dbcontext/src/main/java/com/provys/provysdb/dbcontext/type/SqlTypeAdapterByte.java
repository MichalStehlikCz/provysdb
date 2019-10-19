package com.provys.provysdb.dbcontext.type;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterByte extends SqlTypeAdapterBase<Byte> {
    @Nonnull
    @Override
    protected Byte readValueInternal(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getByte(columnIndex);
    }

    @Override
    protected void bindValueInternal(PreparedStatement statement, int parameterIndex, Byte value) throws SQLException {
        statement.setByte(parameterIndex, value);
    }

    @Override
    public Class<Byte> getType() {
        return Byte.class;
    }

    @Override
    public int getSqlType() {
        return Types.INTEGER;
    }
}
