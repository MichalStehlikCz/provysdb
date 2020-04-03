package com.provys.db.sqldb.query;

import com.provys.db.query.elements.Expression;

/**
 * Expression builder allows to append expression text to code builder.
 *
 * @param <F> is specialisation of statement factory this builder can work for
 * @param <T> is type of expression builder can handle
 */
public interface ExpressionSqlBuilder<F extends StatementFactory, T extends Expression<?>> extends
    ElementSqlBuilder<F, T> {

}
