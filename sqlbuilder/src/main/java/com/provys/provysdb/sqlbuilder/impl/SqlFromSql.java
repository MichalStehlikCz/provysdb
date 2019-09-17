package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.*;

public class SqlFromSql extends SqlFromBase {

    @Nonnull
    private final String sql;
    @Nonnull
    private final List<BindVariable> binds;

    SqlFromSql(String sql, SqlTableAlias alias) {
        super(alias);
        this.sql = Objects.requireNonNull(sql);
        this.binds = Collections.emptyList();
    }

    SqlFromSql(String sql, SqlTableAlias alias, Collection<BindVariable> binds) {
        super(alias);
        this.sql = Objects.requireNonNull(sql);
        this.binds = new ArrayList<>(binds);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.appendWrapped(sql, 2).append(' ').append(getAlias());
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.unmodifiableList(binds);
    }
}
