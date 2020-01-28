package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

class SqlColumnSql extends SqlColumnBase {

    @Nonnull
    private final String sql;
    @Nonnull
    private final List<BindName> binds;

    SqlColumnSql(String sql, @Nullable SqlIdentifier alias) {
        super(alias);
        this.sql = Objects.requireNonNull(sql);
        this.binds = Collections.emptyList();
    }

    SqlColumnSql(String sql, @Nullable SqlIdentifier alias, List<BindName> binds) {
        super(alias);
        this.sql = Objects.requireNonNull(sql);
        this.binds = new ArrayList<>(binds);
    }

    @Override
    public void addSql(CodeBuilder builder) {
        builder.appendWrapped(sql, 4);
        builder.addBinds(binds);
    }

    @Nonnull
    @Override
    public SqlColumn withAlias(SqlIdentifier alias) {
        if (getAlias().filter(al -> al.equals(alias)).isPresent()) {
            return this;
        }
        return new SqlColumnSql(sql, alias);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqlColumnSql that = (SqlColumnSql) o;
        return sql.equals(that.sql) &&
                binds.equals(that.binds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, binds);
    }

    @Override
    public String toString() {
        return "SqlColumnSql{" +
                "sql='" + sql + '\'' +
                ", binds=" + binds +
                "} " + super.toString();
    }
}
