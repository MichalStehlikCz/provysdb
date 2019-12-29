package com.provys.provysdb.dbcontext.type;

import com.provys.common.datatype.DtUid;
import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.SqlTypeAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Types;

public class SqlTypeAdapterDtUid implements SqlTypeAdapter<DtUid> {

    private static final SqlTypeAdapterDtUid INSTANCE = new SqlTypeAdapterDtUid();

    /**
     * @return instance of this type adapter
     */
    public static SqlTypeAdapterDtUid getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<DtUid> getType() {
        return DtUid.class;
    }

    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

    @Nonnull
    @Override
    public DtUid readNonnullValue(DbResultSet resultSet, int columnIndex) {
        return resultSet.getNonnullDtUid(columnIndex);
    }

    @Nonnull
    @Override
    public DtUid readNonnullValue(DbResultSet resultSet, String columnLabel) {
        return resultSet.getNonnullDtUid(columnLabel);
    }

    @Nullable
    @Override
    public DtUid readNullableValue(DbResultSet resultSet, int columnIndex) {
        return resultSet.getNullableDtUid(columnIndex);
    }

    @Nullable
    @Override
    public DtUid readNullableValue(DbResultSet resultSet, String columnLabel) {
        return resultSet.getNullableDtUid(columnLabel);
    }

    @Override
    public void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable DtUid value) {
        statement.setNullableDtUid(parameterIndex, value);
    }
}