package com.provys.provysdb.dbcontext;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
