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
        this.conditions = conditions.stream().filter(condition -> !condition.isEmpty()).collect(Collectors.toList());
    }

    @Override
    public SqlWhereJoiner add(SqlWhere sqlWhere) {
        if (!sqlWhere.isEmpty()) {
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
        return new SqlWhereJoined(operator, conditions);
    }
}
