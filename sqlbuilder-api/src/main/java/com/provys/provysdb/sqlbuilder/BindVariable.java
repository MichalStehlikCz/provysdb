package com.provys.provysdb.sqlbuilder;

import com.provys.provysdb.dbcontext.DbPreparedStatement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface BindVariable extends BindName {

    @Nonnull
    Class<?> getType();

    /**
     * Get value assigned to bind variable; this variant does runtime type conversion and thus is unsafe. Typed variant
     * of this method (defined in BindVariableT interface) should be used whenever possible
     *
     * @param type is type parameter should have
     * @param <T> is type parameter, representing type of returned value
     * @return value associated with this bind value, empty optional if no value has been defined yet
     */
    @Nonnull
    <T> Optional<T> getValue(Class<T> type);

    /**
     * Method can be used when constructing statement and merging its parts. It combines two bind values; they should
     * have the same name and type. It verifies their values; if they have different non-null values, exception is
     * raised. Otherwise it uses one of variables to be combined, preferring one with the value
     *
     * @param other is bind variable this variable should be combined with
     * @return this or other bind variable, depending which has more complete information
     */
    @Nonnull
    BindVariable combine(BindName other);

    /**
     * Bind this variable to specified position in prepared statement
     *
     * @param statement is prepared statement value should be bound to
     * @param parameterIndex is index of parameter value should be bound to
     */
    void bind(DbPreparedStatement statement, int parameterIndex);
}
