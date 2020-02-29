package com.provys.provysdb.sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Typed version of bind variable interface.
 *
 * @param <T> is type of value this bind variable can hold
 */
public interface BindValueT<T> extends BindValue, ExpressionT<T> {

    /**
     * Type associated with this bind value.
     *
     * @return type associated with bind variable
     */
    @Override
    Class<T> getType();

    /**
     * @return value assigned to bind variable, empty optional if no or null value has been assigned.
     */
    @Nullable T getValue();

    BindValueT<T> withValue(@Nullable T newValue);

    @Override
    BindValueT<T> combine(BindName other);
}
