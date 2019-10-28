package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.Condition;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConditionJoined implements Condition {

    @Nonnull
    private final SqlConditionOperator operator;
    @Nonnull
    private final List<Condition> conditions;

    ConditionJoined(SqlConditionOperator operator, Collection<Condition> conditions) {
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
    Collection<Condition> getConditions() {
        return Collections.unmodifiableCollection(conditions);
    }

    @Override
    public boolean isEmpty() {
        return conditions.isEmpty();
    }
}
