package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.Select;

import javax.annotation.Nonnull;
import java.util.*;

class SelectImpl implements Select {

    @Nonnull
    private final String sql;
    @Nonnull
    private final List<BindVariable> binds;

    SelectImpl(String sql) {
        this.sql = Objects.requireNonNull(sql);
        this.binds = Collections.emptyList();
    }

    SelectImpl(String sql, Collection<BindVariable> binds) {
        this.sql = Objects.requireNonNull(sql);
        this.binds = new ArrayList<>(binds);
    }

    @Nonnull
    @Override
    public String getSql() {
        return sql;
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.unmodifiableList(binds);
    }
}
