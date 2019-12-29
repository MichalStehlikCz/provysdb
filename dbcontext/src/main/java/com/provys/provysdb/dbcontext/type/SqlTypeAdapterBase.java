package com.provys.provysdb.dbcontext.type;

import com.provys.provysdb.dbcontext.DbPreparedStatement;
import com.provys.provysdb.dbcontext.DbResultSet;
import com.provys.provysdb.dbcontext.SqlException;
import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.Optional;

public abstract class SqlTypeAdapterBase<T> implements SqlTypeAdapter<T> {

    private static final Logger LOG = LogManager.getLogger(SqlTypeAdapterBase.class);

    @Nonnull
    public Optional<String> getTypeName() {
        return Optional.empty();
    }

    @SuppressWarnings("WeakerAccess")
    @Nullable
    protected abstract T readValueInternal(DbResultSet resultSet, int columnIndex) throws SQLException;

    @Nonnull
    @Override
    public T readNonnullValue(DbResultSet resultSet, int columnIndex) {
        try {
            var value = readValueInternal(resultSet, columnIndex);
            if (resultSet.wasNull() || (value == null)) {
                throw new SqlException(LOG, "Mandatory column value was null (" + columnIndex + ", " +
                        resultSet.getMetaData().getColumnName(columnIndex) + ")");
            }
            return value;
        } catch (SQLException e) {
            throw new SqlException(LOG, "Exception reading from result set", e);
        }
    }

    @Nonnull
    @Override
    public T readNonnullValue(DbResultSet resultSet, String columnLabel) {
        try {
            return readNonnullValue(resultSet, resultSet.findColumn(columnLabel));
        } catch (SQLException e) {
            throw new SqlException(LOG, "Column " + columnLabel + " not found");
        }
    }

    @Nullable
    @Override
    public T readNullableValue(DbResultSet resultSet, int columnIndex) {
        try {
            var value = readValueInternal(resultSet, columnIndex);
            if (resultSet.wasNull()) {
                return null;
            }
            return value;
        } catch (SQLException e) {
            throw new SqlException(LOG, "Exception reading from result set", e);
        }
    }

    @Nullable
    @Override
    public T readNullableValue(DbResultSet resultSet, String columnLabel) {
        try {
            return readNullableValue(resultSet, resultSet.findColumn(columnLabel));
        } catch (SQLException e) {
            throw new SqlException(LOG, "Column " + columnLabel + " not found");
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected abstract void bindValueInternal(DbPreparedStatement statement, int parameterIndex, T value)
            throws SQLException;

    @Override
    public void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable T value) {
        try {
            if (value == null) {
                var typeName = getTypeName();
                if (typeName.isPresent()) {
                    statement.setNull(parameterIndex, getSqlType(), typeName.get());
                } else {
                    statement.setNull(parameterIndex, getSqlType());
                }
            } else {
                bindValueInternal(statement, parameterIndex, value);
            }
        } catch (SQLException e) {
            throw new SqlException(LOG,
                    "Error binding variable (" + statement + ", " + parameterIndex + ", " + value + ")", e);
        }
    }
}
