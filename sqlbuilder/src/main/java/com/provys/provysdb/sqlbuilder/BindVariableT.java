package com.provys.provysdb.sqlbuilder;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface BindVariableT<T> extends BindVariable, ExpressionT<T> {

    @Override
    @Nonnull
    Class<T> getType();

    /**
     * @return value assigned to bind variable, empty optional if no value has been assigned yet
     */
    @Nonnull
    Optional<T> getValue();
}
