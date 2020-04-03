package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Expression;

/**
 * Expression builder allows to append expression text to code builder.
 *
 * @param <B> is type of sql builder this expression builder is able to use
 * @param <T> is type of expression this builder can handle
 */
public interface ExpressionSqlBuilder<B extends SqlBuilder<?>, T extends Expression<?>> extends
    ElementSqlBuilder<B, T> {

}
