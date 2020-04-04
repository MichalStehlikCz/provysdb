package com.provys.db.sqlquery.query;

import com.provys.db.query.elements.Expression;

/**
 * Expression builder allows to append expression text to code builder.
 *
 * @param <B> is type of sql builder this expression builder is able to use
 * @param <T> is type of expression this builder can handle
 */
public interface ExpressionTypeSqlBuilder<B extends SqlBuilder<?>, T extends Expression<?>> extends
    ElementTypeSqlBuilder<B, T> {

}
