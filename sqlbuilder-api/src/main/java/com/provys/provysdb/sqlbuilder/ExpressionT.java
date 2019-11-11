package com.provys.provysdb.sqlbuilder;

import java.util.Optional;

/**
 * Version of expression typed by statement's return type
 *
 * @param <T> is type to which expression evaluates (or more exactly Java class that best corresponds to SQL type this
 *           expression evaluates to)
 */
public interface ExpressionT<T> extends Expression {

    default ExpressionT<Optional<T>> asNullable() {
        //noinspection unchecked - in sql, non-null expression can be used in any place nullable expression of the same type can be used
        return (ExpressionT<Optional<T>>) this;
    }
}
