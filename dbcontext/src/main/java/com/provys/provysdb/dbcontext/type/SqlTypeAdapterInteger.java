package com.provys.provysdb.dbcontext.type;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterInteger extends SqlTypeAdapterBase<Integer> {
    @Nonnull
    @Override
    protected Integer readValueInternal(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getInt(columnIndex);
    }

    @Override
    protected void bindValueInternal(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
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
