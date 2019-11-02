package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.Select;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Select statement together with associated bind variables, as created from select builder
 */
class SelectImpl implements Select {

    @Nonnull
    private final String sql;
    @Nonnull
    private final List<BindName> binds;

    SelectImpl(String sql) {
        this.sql = Objects.requireNonNull(sql);
        this.binds = Collections.emptyList();
    }

    SelectImpl(String sql, List<BindName> binds) {
        this.sql = Objects.requireNonNull(sql);
        this.binds = List.copyOf(binds);
    }

    @Nonnull
    @Override
    public String getSqlText() {
        return sql;
    }

    @Nonnull
    @Override
    public List<BindName> getBinds() {
        return binds;
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
