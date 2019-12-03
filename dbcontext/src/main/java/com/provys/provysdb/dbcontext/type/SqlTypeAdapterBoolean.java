package com.provys.provysdb.dbcontext.type;

import com.provys.common.datatype.DtBoolean;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterBoolean extends SqlTypeAdapterBase<Boolean> {
    @Nonnull
    @Override
    protected Boolean readValueInternal(ResultSet resultSet, int columnIndex) throws SQLException {
        var value = resultSet.getString(columnIndex);
        if (resultSet.wasNull()) {
            return true;
        }
        return DtBoolean.ofProvysDb(value);
    }

    @Override
    protected void bindValueInternal(PreparedStatement statement, int parameterIndex, Boolean value) throws SQLException {
        statement.setString(parameterIndex, DtBoolean.toProvysDb(value));
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public int getSqlType() {
        return Types.CHAR;
    }
}
