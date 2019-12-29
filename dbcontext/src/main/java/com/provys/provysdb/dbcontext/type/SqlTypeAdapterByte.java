package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterByte extends SqlTypeAdapterBase<Byte> {

    private static final SqlTypeAdapterByte INSTANCE = new SqlTypeAdapterByte();

    /**
     * @return instance of this type adapter
     */
    public static SqlTypeAdapterByte getInstance() {
        return INSTANCE;
    }

    @Nonnull
    @Override
    protected Byte readValueInternal(DbResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getByte(columnIndex);
    }

    @Override
    protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex, Byte value) throws SQLException {
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
