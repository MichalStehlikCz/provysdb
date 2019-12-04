package com.provys.provysdb.sqlbuilder;

/**
 * Version of expression typed by statement's return type
 *
 * @param <T> is type to which expression evaluates (or more exactly Java class that best corresponds to SQL type this
 *           expression evaluates to)
 */
@SuppressWarnings({"unused", "squid:S2326"})
public interface ExpressionT<T> extends Expression {
}
