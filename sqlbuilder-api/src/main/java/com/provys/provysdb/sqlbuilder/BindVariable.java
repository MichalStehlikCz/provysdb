package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface BindVariable extends Expression {

    @Nonnull
    SqlName getName();

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
     * Return bind value with the same name and type, but with new value. Used as part of bind value method. Specified
     * object must be of the type compatible with bind variable
     *
     * @param value is value to be assigned to new bind
     * @return bind variable with the same name as old one, but with the new value
     */
    @Nonnull
    BindVariable withValue(@Nullable Object value);

    /**
     * Method can be used when constructing statement and merging its parts. It combines two bind values; they should
     * have the same name and type. It verifies their values; if they have different non-null values, exception is
     * raised. Otherwise it uses one of variables to be combined, preferring one with the value
     *
     * @param other is bind variable this variable should be combined with
     * @return this or other bind variable, depending which has more complete information
     */
    @Nonnull
    BindVariable combine(BindVariable other);
}
