package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

public class SelectBuilderT1Impl<T1> extends SelectBuilderTImpl<SelectBuilderT1Impl<T1>>
        implements SelectBuilderT1<T1> {

    private final SqlColumnT<T1> column1;

    SelectBuilderT1Impl(Sql sql, SqlColumnT<T1> column1) {
        super(sql);
        this.column1 = column1;
    }

    SelectBuilderT1Impl(Sql sql, SqlColumnT<T1> column1, List<SqlFrom> tables, Collection<Condition> conditions) {
        super(sql, List.of(column1), tables, conditions);
        this.column1 = column1;
    }

    @Nonnull
    @Override
    SelectBuilderT1Impl<T1> self() {
        return this;
    }

    @Nonnull
    @Override
    public List<SqlColumn> getColumns() {
        return List.of(column1);
    }

    @Nonnull
    @Override
    protected List<SqlColumn> getModifiableColumns() {
        return List.of(column1);
    }

    @Nonnull
    @Override
    public SelectBuilderT1Impl<T1> copy() {
        return new SelectBuilderT1Impl<>(sql, column1, tables, conditions);
    }
}
