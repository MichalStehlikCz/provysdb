package com.provys.provysdb.sqlbuilder;

import com.provys.provysdb.dbcontext.DbResultSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Class corresponds to PreparedStatement, built from Statement. It allows to bind values to variables, execute
 * statement and access resulting data
 */
public interface SelectStatement extends AutoCloseable {

    /**
     *
     * @return collection of bind variables, associated with this statement (and available for binding)
     */
    Collection<BindName> getBinds();

        /**
         * Bind value to variable with specified name
         *
         * @param bind is name of bind
         * @param value is supplied value
         * @return self to allow chaining
         */
    @Nonnull
    SelectStatement bindValue(String bind, @Nullable Object value);

    /**
     * Bind value to specified bind variable
     *
     * @param bind is bind variable
     * @param value is supplied value
     * @return self to allow chaining
     */
    @Nonnull
    <T> SelectStatement bindValue(BindVariableT<T> bind, @Nullable T value);

    /**
     * Execute associated statement (using connection fetched from underlying DataSource) and return resulting ResultSet
     *
     * @return ResultSet containing data, retrieved by query
     */
    DbResultSet execute();

    /**
     * Close statement. Will close underlying connection if it was initialized internally and not supplied to
     * constructor.
     */
    void close();
}
