package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlWhere;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SqlWhereJoined implements SqlWhere {

    @Nonnull
    private final SqlConditionOperator operator;
    @Nonnull
    private final List<SqlWhere> conditions;

    SqlWhereJoined(SqlConditionOperator operator, Collection<SqlWhere> conditions) {
        this.operator = operator;
        this.conditions = new ArrayList<>(conditions);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        if (conditions.size() > 1) {
            builder.append('(').appendLine().increasedIdent("", operator.toString() + " ", 2);
        }
        for (var condition : conditions) {
            condition.addSql(builder);
            builder.appendLine();
        }
        if (conditions.size() > 1) {
            builder.popIdent().append(')');
        }
    }

    @Nonnull
    SqlConditionOperator getOperator() {
        return operator;
    }

    @Nonnull
    Collection<SqlWhere> getConditions() {
        return Collections.unmodifiableCollection(conditions);
    }

    @Override
    public boolean isEmpty() {
        return conditions.isEmpty();
    }
}
