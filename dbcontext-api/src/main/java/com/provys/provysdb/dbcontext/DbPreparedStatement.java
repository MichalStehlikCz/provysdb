package com.provys.provysdb.dbcontext;

import com.provys.common.datatype.DtUid;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Provys extension of JDBC's prepared statement.
 * Built on Provys connection, adds support for work with Provys specific data-types via parametrised setValue method
 */
public interface DbPreparedStatement extends PreparedStatement, DbStatement {

    @Override
    DbResultSet executeQuery() throws SQLException;

    /**
     * Set (mandatory) value to bind value of database boolean type
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SqlException when any problem is encountered
     */
    void setNonnullDbBoolean(int parameterIndex, boolean value);

    /**
     * Set optional value to bind value of database boolean type
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SqlException when any problem is encountered
     */
    void setNullableDbBoolean(int parameterIndex, @Nullable Boolean value);

    /**
     * Set (mandatory) value to bind value of provys boolean type (char Y / N / NULL)
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SqlException when any problem is encountered
     */
    void setNonnullBoolean(int parameterIndex, boolean value);

    /**
     * Set value to bind value of provys boolean type (char Y / N / NULL)
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SqlException when any problem is encountered
     */
    void setNullableBoolean(int parameterIndex, @Nullable Boolean value);

    /**
     * Set (mandatory) value to bind value of provys Uid type
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SqlException when any problem is encountered
     */
    void setNonnullDtUid(int parameterIndex, DtUid value);

    /**
     * Set (optional) value to bind value of provys Uid type
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SqlException when any problem is encountered
     */
    void setNullableDtUid(int parameterIndex, @Nullable DtUid value);

    /**
     * Set mandatory value to bind value, use default conversion of supplied type to Sql.\Note that type will be
     * inferred from value and thus might not correspond to formal parameter type
     *
     * @param parameterIndex is index of bind value in statement
     * @param value is value to be bound
     */
    void setNonnullValue(int parameterIndex, Object value);

    /**
     * Set optional value to bind value, use default conversion of supplied type to Sql
     *
     * @param parameterIndex is index of bind value in statement
     * @param value is value to be bound
     * @param type is type of supplied value
     * @param <T> is (Java) type of value to be bound
     */
    <T> void setNullableValue(int parameterIndex, @Nullable T value, Class<T> type);

    /**
     * Get type adapter map usable for given statement
     *
     * @return type adapter map associated with context this statement belongs to
     */
    SqlTypeMap getAdapterMap();
}
