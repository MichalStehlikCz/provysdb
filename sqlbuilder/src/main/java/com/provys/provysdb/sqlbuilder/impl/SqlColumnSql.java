package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;

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
        getAlias().ifPresent(alias -> builder.append(' ').append(alias));
        builder.addBinds(binds);
    }
}
