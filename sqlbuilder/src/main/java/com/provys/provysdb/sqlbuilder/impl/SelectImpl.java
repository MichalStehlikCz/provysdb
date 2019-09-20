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
    public String getSqlText() {
        return sql;
    }

    @Nonnull
    @Override
    public Collection<BindVariable> getBinds() {
        return Collections.unmodifiableList(binds);
    }

    @Override
    public int getPositions() {
        return 0;
    }

    @Override
    public Map<BindVariable, List<Integer>> getBindPositions() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectImpl select = (SelectImpl) o;
        return sql.equals(select.sql) &&
                binds.equals(select.binds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, binds);
    }

    @Override
    public String toString() {
        return "SelectImpl{" +
                "sql='" + sql + '\'' +
                ", binds=" + binds +
                '}';
    }
}
