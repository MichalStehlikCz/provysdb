package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;

public class SqlFromSelect extends SqlFromBase {

    @Nonnull
    private final Select select;

    SqlFromSelect(Select select, SqlTableAlias alias) {
        super(alias);
        this.select = Objects.requireNonNull(select);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.append('(').appendLine()
                .increasedIdent(2)
                .appendWrapped(select.getSql())
                .popIdent()
                .append(") ")
                .append(getAlias());
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return select.getBinds();
    }
}
