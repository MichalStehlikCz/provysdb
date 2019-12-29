package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.sql.Types;

public class SqlTypeAdapterString extends SqlTypeAdapterBase<String> {

    private static final SqlTypeAdapterString INSTANCE = new SqlTypeAdapterString();

    /**
     * @return instance of this type adapter
     */
    public static SqlTypeAdapterString getInstance() {
        return INSTANCE;
    }

    @Nonnull
    @Override
    protected String readValueInternal(DbResultSet resultSet, int columnIndex) throws SQLException {
        return resultSet.getString(columnIndex);
    }

    @Override
    protected void bindValueInternal(DbPreparedStatement statement, int parameterIndex, String value) throws SQLException {
        statement.setString(parameterIndex, value);
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }
}
