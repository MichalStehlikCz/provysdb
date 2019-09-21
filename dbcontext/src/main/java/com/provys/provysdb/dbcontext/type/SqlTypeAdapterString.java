package com.provys.provysdb.dbcontext.type;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterString extends SqlTypeAdapterBase<String> {
    @Nonnull
    @Override
    protected String readValueInternal(ResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    @Override
    protected void bindValueInternal(PreparedStatement statement, int parameterIndex, String value) throws SQLException {
        statement.setString(parameterIndex, value);
    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }
}
