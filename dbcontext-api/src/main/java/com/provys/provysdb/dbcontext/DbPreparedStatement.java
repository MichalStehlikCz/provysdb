package com.provys.provysdb.dbcontext;

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
     * Set (mandatory) value to bind value of provys boolean type (char Y / N / NULL)
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SQLException when any problem is encountered
     */
    void setDtBoolean(int parameterIndex, boolean value) throws SQLException;

    /**
     * Set value to bind value of provys boolean type (char Y / N / NULL)
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SQLException when any problem is encountered
     */
    void setNullableDtBoolean(int parameterIndex, @Nullable Boolean value) throws SQLException;

    /**
     * Set (mandatory) value to bind value of provys Uid type
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SQLException when any problem is encountered
     */
    void setDtUid(int parameterIndex, BigInteger value) throws SQLException;

    /**
     * Set (optional) value to bind value of provys Uid type
     *
     * @param parameterIndex is index of bind variable
     * @param value is value to be bound
     * @throws SQLException when any problem is encountered
     */
    void setNullableDtUid(int parameterIndex, @Nullable BigInteger value) throws SQLException;

    /**
     * Set (optional) value to bind value, use default conversion of supplied type to Sql
     *
     * @param parameterIndex is index of bind value in statement
     * @param type is type of supplied value
     * @param value is value to be bound
     * @param <T> is (Java) type of value to be bound
     */
    <T> void setValue(int parameterIndex, Class<T> type, @Nullable T value);

    /**
     * Get type adapter map usable for given statement
     *
     * @return type adapter map associated with context this statement belongs to
     */
    SqlTypeMap getAdapterMap();
}
