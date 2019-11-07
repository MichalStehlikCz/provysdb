package com.provys.provysdb.dbcontext.type;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.dbcontext.SqlTypeAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class SqlTypeAdapterBase<T> implements SqlTypeAdapter<T> {

    private static final Logger LOG = LogManager.getLogger(SqlTypeAdapterBase.class);

    @Nonnull
    public Optional<String> getTypeName() {
        return Optional.empty();
    }

    @SuppressWarnings("WeakerAccess")
    @Nonnull
    protected abstract T readValueInternal(ResultSet resultSet, int columnIndex) throws SQLException;

    @Nonnull
    @Override
    public T readValue(ResultSet resultSet, int columnIndex) {
        try {
            var value = readValueInternal(resultSet, columnIndex);
            if (resultSet.wasNull()) {
                throw new InternalException(LOG, "Mandatory column value was null (" + columnIndex + ", " +
                        resultSet.getMetaData().getColumnName(columnIndex) + ")");
            }
            return value;
        } catch (SQLException e) {
            throw new InternalException(LOG, "Exception reading from result set", e);
        }
    }

    @Nonnull
    @Override
    public T readValue(ResultSet resultSet, String columnLabel) {
        try {
            return readValue(resultSet, resultSet.findColumn(columnLabel));
        } catch (SQLException e) {
            throw new InternalException(LOG, "Column " + columnLabel + " not found");
        }
    }

    @Nonnull
    @Override
    public Optional<T> readOptionalValue(ResultSet resultSet, int columnIndex) {
        try {
            var value = readValueInternal(resultSet, columnIndex);
            if (resultSet.wasNull()) {
                return Optional.empty();
            }
            return Optional.of(value);
        } catch (SQLException e) {
            throw new InternalException(LOG, "Exception reading from result set", e);
        }
    }

    @Nonnull
    @Override
    public Optional<T> readOptionalValue(ResultSet resultSet, String columnLabel) {
        try {
            return readOptionalValue(resultSet, resultSet.findColumn(columnLabel));
        } catch (SQLException e) {
            throw new InternalException(LOG, "Column " + columnLabel + " not found");
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected abstract void bindValueInternal(PreparedStatement statement, int parameterIndex, T value)
            throws SQLException;

    @Override
    public void bindValue(PreparedStatement statement, int parameterIndex, @Nullable T value) {
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
            throw new InternalException(LOG,
                    "Error binding variable (" + statement + ", " + parameterIndex + ", " + value + ")", e);
        }
    }
}
