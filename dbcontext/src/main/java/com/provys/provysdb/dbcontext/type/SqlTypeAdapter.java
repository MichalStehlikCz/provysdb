package com.provys.provysdb.dbcontext.type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Defines interface, needed to support reading values of given type from DbResultSet and binding them to
 * DbPreparedStatement
 *
 * @param <T> is type this adapter is associated with
 */
public interface SqlTypeAdapter<T> {

    /**
     * @return class this adapter is associated with
     */
    Class<T> getType();

    /**
     * @return sql type associated with this adapter
     */
    int getSqlType();

    /**
     * @return name of type associated with this adapter (on JDBC level), empty optional for non-object types
     */
    @Nonnull
    Optional<String> getTypeName();

    /**
     * Read value from result set. Throw exception when value in given column is null.
     *
     * @param resultSet is result set value should be read from
     * @param columnIndex is column value should be read from
     * @return read column value
     */
    @Nonnull
    T readValue(ResultSet resultSet, int columnIndex);

    /**
     * Read value from result set. Throw exception when value in given column is null.
     *
     * @param resultSet is result set value should be read from
     * @param columnLabel is name of column value should be read from
     * @return read column value
     */
    @Nonnull
    T readValue(ResultSet resultSet, String columnLabel);

    /**
     * Read value from result set, allow reading null values
     *
     * @param resultSet is result set value should be read from
     * @param columnIndex is column value should be read from
     * @return read column value
     */
    @Nonnull
    Optional<T> readOptionalValue(ResultSet resultSet, int columnIndex);

    /**
     * Read value from result set, allow reading null values
     *
     * @param resultSet is result set value should be read from
     * @param columnLabel is name of column value should be read from
     * @return read column value
     */
    @Nonnull
    Optional<T> readOptionalValue(ResultSet resultSet, String columnLabel);

    /**
     * Bind value to variable
     *
     * @param statement is prepared statement where value should be bind
     * @param parameterIndex is index of parameter whose value should be set
     * @param value is value to be set
     */
    void bindValue(PreparedStatement statement, int parameterIndex, @Nullable T value);
}
