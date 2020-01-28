package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.ExpressionT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Backing functions for COALESCE SQL function
 *
 * @param <T> is type of expression, as determined by the first operand
 */
class FuncCoalesce<T> implements ExpressionT<T> {

    private final List<ExpressionT<? extends T>> expressionList;

    @SafeVarargs
    FuncCoalesce(ExpressionT<T> expression0, ExpressionT<? extends T>... expressions) {
        expressionList = new ArrayList<>(expressions.length + 1);
        expressionList.add(expression0);
        Collections.addAll(expressionList, expressions);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append("COALESCE(");
        boolean first = true;
        for (var expression : expressionList) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            expression.addSql(builder);
        }
        builder.append(')');
    }
}
