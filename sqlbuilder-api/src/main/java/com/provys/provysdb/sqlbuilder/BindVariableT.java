package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Typed version of bind variable interface
 * @param <T> is type of value this bind variable can hold
 */
public interface BindVariableT<T> extends BindVariable, ExpressionT<T> {

    /**
     * @return type associated with bind variable
     */
    @Override
    @Nonnull
    Class<T> getType();

    /**
     * @return value assigned to bind variable, empty optional if no value has been assigned yet
     */
    @Nonnull
    Optional<T> getValue();

    @Nonnull
    BindVariableT<T> withValue(@Nullable Object value);
}
