package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.SqlWhere;
import com.provys.provysdb.sqlbuilder.SqlWhereJoiner;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class SqlWhereJoinerImpl implements SqlWhereJoiner {

    @Nonnull
    private final SqlConditionOperator operator;
    @Nonnull
    private final List<SqlWhere> conditions;

    SqlWhereJoinerImpl(SqlConditionOperator operator) {
        this.operator = Objects.requireNonNull(operator);
        this.conditions = new ArrayList<>(4);
    }

    SqlWhereJoinerImpl(SqlConditionOperator operator, Collection<SqlWhere> conditions) {
        this.operator = Objects.requireNonNull(operator);
        if (operator == SqlConditionOperator.AND) {
            // in AND, we skip trivial condition
            this.conditions = conditions.stream().filter(condition -> !condition.isEmpty()).collect(Collectors.toList());
        } else {
            // in OR, trivial condition is handled in build
            this.conditions = new ArrayList<>(conditions);
        }
    }

    @Override
    public SqlWhereJoiner add(SqlWhere sqlWhere) {
        // in case of OR, trivial condition is handled in build; in AND, we can just skip it
        if (!sqlWhere.isEmpty() || (operator == SqlConditionOperator.OR)) {
            conditions.add(sqlWhere);
        }
        return this;
    }

    @Override
    public SqlWhere build() {
        if (conditions.isEmpty()) {
            return SqlWhereEmpty.getInstance();
        }
        if (conditions.size() == 1) {
            return conditions.get(0);
        }
        // in case of OR, we handle trivial condition here...
        if ((operator == SqlConditionOperator.OR) && (conditions.stream().anyMatch(SqlWhere::isEmpty))) {
            return SqlWhereEmpty.getInstance();
        }
        return new SqlWhereJoined(operator, conditions);
    }
}
