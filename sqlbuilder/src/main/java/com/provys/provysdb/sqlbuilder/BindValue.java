package com.provys.provysdb.sqlbuilder;

import java.util.Optional;

/**
 * Bind value extends BindName by connecting type and value to named bind
 */
public interface BindValue extends BindName {

    Class<?> getType();

    /**
     * Get value assigned to bind variable; this variant does runtime type conversion and thus is unsafe. Typed variant
     * of this method (defined in BindVariableT interface) should be used whenever possible
     *
     * @param type is type parameter should have
     * @param <T> is type parameter, representing type of returned value
     * @return value associated with this bind value, empty optional if no value has been defined yet
     */
    <T> Optional<T> getValue(Class<T> type);

    /**
     * Method can be used when constructing statement and merging its parts. It combines two bind values; they should
     * have the same name and type. It verifies their values; if they have different non-null values, exception is
     * raised. Otherwise it uses one of variables to be combined, preferring one with the value
     *
     * @param other is bind variable this variable should be combined with
     * @return this or other bind variable, depending which has more complete information
     */
    @Override
    BindValue combine(BindName other);
}
